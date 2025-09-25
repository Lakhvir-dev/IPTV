package com.dhiman.iptv.activity.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.ActivityPlayerBinding
import com.dhiman.iptv.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private var countDownTimer: CountDownTimer? = null
    private var channelDetailsCountDownTimer: CountDownTimer? = null
    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModels()
    private var resumeMovieTime: Long = 0
    private lateinit var exoplayer: ExoPlayer
    private var intentType = ""
    private var selectedPos = 0
    private var dataArrayList = ArrayList<String>()
    private var channelIdsArrayList = ArrayList<String>()
    private var channelsNameArrayList = ArrayList<String>()
    private var channelId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player)
        binding.viewModel = viewModel

        /** Player Setup */
        setupPlayer()

        /** Observer Listeners */
        observerListeners()

        /** Click Listeners */
        clickListeners()
    }

    private fun clickListeners() {
        binding.apply {
            playerView.setOnClickListener {
                if (!playerView.useController) {
                    playerView.useController = true
                }
                hideUnlockButton()
            }
        }
    }

    /**
     * Observer Listeners
     */
    @SuppressLint("SetTextI18n")
    private fun observerListeners() {
        viewModel.channelEPGLiveData.observe(this) {
            if (it.status == Status.SUCCESS) {
                it.data?.epgListings?.let { list ->
                    if (list.isNotEmpty()) {
                        val modelFirst = list[0]
                        binding.tvNowPlaying.text =
                            "Now Playing: " + modelFirst.title?.decodeBase64() + "     " +
                                    modelFirst.start?.getConvertedDateTime(
                                        "yyyy-MM-dd hh:mm:ss",
                                        "hh:mm a"
                                    ) + " - " +
                                    modelFirst.end?.getConvertedDateTime(
                                        "yyyy-MM-dd hh:mm:ss", "hh:mm a"
                                    )

                        if (list.size >= 2) {
                            val modelSecond = list[1]
                            binding.tvNextPlaying.text =
                                "Next Program: " + modelSecond.title?.decodeBase64() + "     " +
                                        modelSecond.start?.getConvertedDateTime(
                                            "yyyy-MM-dd hh:mm:ss",
                                            "hh:mm a"
                                        ) + " - " +
                                        modelSecond.end?.getConvertedDateTime(
                                            "yyyy-MM-dd hh:mm:ss", "hh:mm a"
                                        )
                        }
                    }
                }
            }
        }
    }

    /**
     * Player Setup
     */
    private fun setupPlayer() {
        exoplayer = ExoPlayer.Builder(this@PlayerActivity)
            .setSeekBackIncrementMs(15000)
            .setSeekForwardIncrementMs(15000)
            .build()

        if (intent.hasExtra(ConstantUtil.INTENT_ID)) {
            val movieFullUrl = intent.getStringExtra(ConstantUtil.INTENT_ID).toString()

            if (intent.hasExtra(ConstantUtil.INTENT_TYPE)) {
                intentType = intent.getStringExtra(ConstantUtil.INTENT_TYPE).toString()
                selectedPos = intent.getIntExtra(ConstantUtil.INTENT_SELECTED_POS, 0)
                dataArrayList =
                    intent.getStringArrayListExtra(ConstantUtil.INTENT_SERIES_EPISODES_LIST) as ArrayList<String>

                if (intentType == ConstantUtil.INTENT_LIVE) {
                    channelIdsArrayList =
                        intent.getStringArrayListExtra(ConstantUtil.INTENT_CHANNEL_ID_LIST) as ArrayList<String>
                    channelsNameArrayList =
                        intent.getStringArrayListExtra(ConstantUtil.INTENT_CHANNEL_NAME_LIST) as ArrayList<String>

                    channelId = channelIdsArrayList[selectedPos]

                    /** Set Channel Name */
                    setChannelName()
                }

                for (x in 0 until dataArrayList.size) {
                    exoplayer.addMediaItem(MediaItem.fromUri(dataArrayList[x]))
                }
                exoplayer.seekToDefaultPosition(selectedPos)

                exoplayer.prepare()
                exoplayer.playWhenReady = true
            } else {
                setupMedia(movieFullUrl)
            }

            binding.playerView.player = exoplayer
            exoplayerListeners()
        }
    }

    /**
     * Exo Player Listeners
     */
    private fun exoplayerListeners() {
        exoplayer.addListener(object : Player.Listener {
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                try {
                    Toast.makeText(this@PlayerActivity, error.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    onBackPressed()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

//        btnDisplay.setOnClickListener {
//            if (binding.playerView.resizeMode != AspectRatioFrameLayout.RESIZE_MODE_FILL) {
//                binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
//            }
//        }
//
//        btnDisplayMode.setOnClickListener {
//            if (binding.playerView.resizeMode != AspectRatioFrameLayout.RESIZE_MODE_FIT) {
//                binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
//            }
//        }
//
//        btnSubtitle.setOnClickListener {
////            val movieSubtitleDialog = MovieSubtitleDialog()
////            movieSubtitleDialog.show(this@PlayerActivity.supportFragmentManager, "")
////            movieSubtitleDialog.callBack = {
////                // startActivity(Intent(this, UserListActivity::class.java))
////            }
//        }

//        setFocusBackground(exoPlayerLockButton)
//        setFocusBackground(binding.btnUnlock)
//        setFocusBackground(btnDisplay)
//        setFocusBackground(btnDisplayMode)
//        setFocusBackground(btnSubtitle)
//        setFocusBackground(exo_rew)
//        setFocusBackground(exo_ffwd)
//        setFocusBackground(exo_play)
//        setFocusBackground(exo_pause)

        binding.mainConstraintLayout.clearFocus()

        binding.playerView.useController = true
        hideUnlockButton()
    }

    /**
     * Setup Focus
     */
    private fun setFocusBackground(selectedView: View) {
        selectedView.isFocusableInTouchMode = true
        selectedView.isFocusable = true
        selectedView.setBackgroundResource(R.color.transparent)
        selectedView.setOnFocusChangeListener { _, b ->
            if (b) {
                selectedView.setBackgroundResource(R.color.white_alpha_60)
            } else {
                selectedView.setBackgroundResource(R.color.transparent)
            }
        }
    }

    /**
     * Setup Media to Exo Player
     */
    private fun setupMedia(movieFullUrl: String) {
        exoplayer.setMediaItem(MediaItem.fromUri(movieFullUrl))
        exoplayer.prepare()
        exoplayer.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    /**
     * Pause Exo Player
     */
    private fun pausePlayer() {
        resumeMovieTime = exoplayer.currentPosition
        exoplayer.seekTo(resumeMovieTime)
        exoplayer.playWhenReady = false
    }

    /**
     * Resume Exo Player
     */
    private fun resumePlayer() {
        exoplayer.seekTo(resumeMovieTime)
        exoplayer.playWhenReady = true
        binding.playerView.showController()
    }

    override fun onResume() {
        super.onResume()
        resumePlayer()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (!binding.playerView.useController) {
            binding.playerView.useController = true
        } else if (binding.playerView.useController) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                exoplayer.seekToPreviousMediaItem()
                try {
                    if (selectedPos > 0) {
                        selectedPos -= 1
                    }
                    channelId = channelIdsArrayList[selectedPos]

                    /** Set Channel Name */
                    setChannelName()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return true
            } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                exoplayer.seekToNextMediaItem()
                try {
                    if (selectedPos < channelsNameArrayList.size - 1) {
                        selectedPos += 1
                    }
                    channelId = channelIdsArrayList[selectedPos]

                    /** Set Channel Name */
                    setChannelName()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return true
            }
        }
        hideUnlockButton()

        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        exoplayer.release()
        super.onDestroy()
    }

    /**
     * Handle Unlock / Lock Button Visibility
     */
    private fun hideUnlockButton() {
        try {
            if (countDownTimer != null) {
                countDownTimer?.cancel()
            }
            countDownTimer = object : CountDownTimer(5000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    binding.playerView.useController = false
                }
            }
            countDownTimer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Handle Show / Hide Channel's Details
     */
    private fun hideShowChannelDetails() {
        try {
            if (channelDetailsCountDownTimer != null) {
                channelDetailsCountDownTimer?.cancel()
            }
            channelDetailsCountDownTimer = object : CountDownTimer(9000, 1000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    binding.llChannelDetails.gone()
                }
            }
            binding.llChannelDetails.visible()
            channelDetailsCountDownTimer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Set Channel Name
     */
    private fun setChannelName() {
        binding.tvTitle.text = channelsNameArrayList[selectedPos]
        binding.tvNowPlaying.text = ""
        binding.tvNextPlaying.text = ""

        /** Get Channel's EPG Api Call */
        viewModel.getChannelEPGApi(channelId)

        /** Handle Show / Hide Channel's Details */
        hideShowChannelDetails()
    }

}