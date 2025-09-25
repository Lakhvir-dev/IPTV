package com.dhiman.iptv.activity.multiScreen

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.live_program_list.LiveProgramListNewActivity
import com.dhiman.iptv.activity.movie_program_list.MovieProgramListActivity
import com.dhiman.iptv.activity.series_program.SeriesProgramActivity
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.ActivityMultiScreenBinding
import com.dhiman.iptv.dialog.multiScreenResource.MultiScreenResourceDialog
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.gone
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MultiScreenActivity : AppCompatActivity(), Player.Listener {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var binding: ActivityMultiScreenBinding
    private val viewModel: MultiScreenVM by viewModels()
    private var selectedGrid = 1

    private lateinit var spvFirst: StyledPlayerView
    private lateinit var spvSecond: StyledPlayerView
    private lateinit var spvThird: StyledPlayerView
    private lateinit var spvFourth: StyledPlayerView
    private lateinit var exoPlayerFirst: ExoPlayer
    private lateinit var exoPlayerSecond: ExoPlayer
    private lateinit var exoPlayerThird: ExoPlayer
    private lateinit var exoPlayerFourth: ExoPlayer

    //    private lateinit var flContainerFirst: FrameLayout
//    private lateinit var flContainerSecond: FrameLayout
//    private lateinit var flContainerThird: FrameLayout
//    private lateinit var flContainerFourth: FrameLayout
    private lateinit var rlFirstGrid: RelativeLayout
    private lateinit var rlSecondGrid: RelativeLayout
    private lateinit var rlThirdGrid: RelativeLayout
    private lateinit var rlFourthGrid: RelativeLayout
    private lateinit var ivFirstAdd: ImageView
    private lateinit var ivSecondAdd: ImageView
    private lateinit var ivThirdAdd: ImageView
    private lateinit var ivFourthAdd: ImageView
    private var selectedScreen = 0
//    private lateinit var firstExoplayerFragment: ExoplayerFragment
//    private lateinit var secondExoplayerFragment: ExoplayerFragment
//    private lateinit var thirdExoplayerFragment: ExoplayerFragment
//    private lateinit var fourthExoplayerFragment: ExoplayerFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_multi_screen
        )
        binding.viewModel = viewModel

//        /** Initialize Fragments */
//        initializeFragment()

        /** Setup Exo Player */
        setupExoPlayer()

        /** Set Grid as per User Selection */
        setupGridMultiScreen()
    }

//    /**
//     * Initialize Fragments
//     */
//    private fun initializeFragment() {
//        firstExoplayerFragment = ExoplayerFragment()
//        secondExoplayerFragment = ExoplayerFragment()
//        thirdExoplayerFragment = ExoplayerFragment()
//        fourthExoplayerFragment = ExoplayerFragment()
//    }

    /**
     * Setup Exo Player
     */
    private fun setupExoPlayer() {
        exoPlayerFirst = ExoPlayer.Builder(this@MultiScreenActivity).build()
        exoPlayerSecond = ExoPlayer.Builder(this@MultiScreenActivity).build()
        exoPlayerThird = ExoPlayer.Builder(this@MultiScreenActivity).build()
        exoPlayerFourth = ExoPlayer.Builder(this@MultiScreenActivity).build()

        exoPlayerFirst.addListener(this)
        exoPlayerSecond.addListener(this)
        exoPlayerThird.addListener(this)
        exoPlayerFourth.addListener(this)
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        try {
            Toast.makeText(this@MultiScreenActivity, error.message.toString(), Toast.LENGTH_LONG)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Setup Media to Exo Player
     */
    private fun setupMedia(movieFullUrl: String, exoplayer: ExoPlayer) {
        exoplayer.setMediaItem(MediaItem.fromUri(movieFullUrl))
        exoplayer.prepare()
    }

    /**
     * Set Grid as per User Selection
     */
    private fun setupGridMultiScreen() {
        selectedGrid = sharedPrefs.getInt(ConstantUtil.MULTI_SCREEN_GRID)

        when (selectedGrid) {
            1 -> {
                binding.viewStub.viewStub?.layoutResource = R.layout.layout_first_grid
            }
            2 -> {
                binding.viewStub.viewStub?.layoutResource = R.layout.layout_second_grid
            }
            3 -> {
                binding.viewStub.viewStub?.layoutResource = R.layout.layout_third_grid
            }
            4 -> {
                binding.viewStub.viewStub?.layoutResource = R.layout.layout_fourth_grid
            }
            5 -> {
                binding.viewStub.viewStub?.layoutResource = R.layout.layout_fifth_grid
            }
            6 -> {
                binding.viewStub.viewStub?.layoutResource = R.layout.layout_sixth_grid
            }
        }

        val view = binding.viewStub.viewStub?.inflate()
        view?.let {
            spvFirst = it.findViewById(R.id.spvFirst)
            spvSecond = it.findViewById(R.id.spvSecond)
            spvThird = it.findViewById(R.id.spvThird)
            spvFourth = it.findViewById(R.id.spvFourth)

//            flContainerFirst = it.findViewById(R.id.flContainerFirst)
//            flContainerSecond = it.findViewById(R.id.flContainerSecond)
//            flContainerThird = it.findViewById(R.id.flContainerThird)
//            flContainerFourth = it.findViewById(R.id.flContainerFourth)

            rlFirstGrid = it.findViewById(R.id.rlFirstGrid)
            rlSecondGrid = it.findViewById(R.id.rlSecondGrid)
            rlThirdGrid = it.findViewById(R.id.rlThirdGrid)
            rlFourthGrid = it.findViewById(R.id.rlFourthGrid)

            ivFirstAdd = it.findViewById(R.id.ivFirstAdd)
            ivSecondAdd = it.findViewById(R.id.ivSecondAdd)
            ivThirdAdd = it.findViewById(R.id.ivThirdAdd)
            ivFourthAdd = it.findViewById(R.id.ivFourthAdd)

            spvFirst.player = exoPlayerFirst
            spvSecond.player = exoPlayerSecond
            spvThird.player = exoPlayerThird
            spvFourth.player = exoPlayerFourth

//            /** Add Fragment in Container */
//            addFragment(R.id.flContainerFirst, firstExoplayerFragment)
//            addFragment(R.id.flContainerSecond, secondExoplayerFragment)
//            addFragment(R.id.flContainerThird, thirdExoplayerFragment)
//            addFragment(R.id.flContainerFourth, fourthExoplayerFragment)

            /** Focus Change Listeners */
            setUpFocusChangeListeners()

            /** Click Listeners */
            clickListeners()
        }

    }

//    /**
//     * Add Fragment in Container
//     */
//    private fun addFragment(frameId: Int, fragment: ExoplayerFragment) {
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.add(frameId, fragment)
//        transaction.commitAllowingStateLoss()
//    }

    /**
     * Click Listeners
     */
    private fun clickListeners() {
        rlFirstGrid.setOnClickListener {
            pauseAllPlayers()
            selectedScreen = 1
            multiScreenResourceDialog()
        }

        rlSecondGrid.setOnClickListener {
            pauseAllPlayers()
            selectedScreen = 2
            multiScreenResourceDialog()
        }

        rlThirdGrid.setOnClickListener {
            pauseAllPlayers()
            selectedScreen = 3
            multiScreenResourceDialog()
        }

        rlFourthGrid.setOnClickListener {
            pauseAllPlayers()
            selectedScreen = 4
            multiScreenResourceDialog()
        }
    }

    /**
     * Multi Screen Resource Selection Dialog
     */
    private fun multiScreenResourceDialog() {
        val dialog = MultiScreenResourceDialog()
        dialog.show(supportFragmentManager, "")
        dialog.isCancelable = false

        dialog.callBack = {
            val intentUrl = when (it) {
                ConstantUtil.INTENT_LIVE -> {
                    Intent(this, LiveProgramListNewActivity::class.java)
                }
                ConstantUtil.INTENT_MOVIES -> {
                    Intent(this, MovieProgramListActivity::class.java)
                }
                ConstantUtil.INTENT_SERIES -> {
                    Intent(this, SeriesProgramActivity::class.java)
                }
                else -> {
                    Intent(this, LiveProgramListNewActivity::class.java)
                }
            }
            intentUrl.putExtra(
                ConstantUtil.INTENT_MULTI_SCREEN_RESOURCE,
                ConstantUtil.INTENT_MULTI_SCREEN_RESOURCE
            )
            activityResult.launch(intentUrl)
        }

        dialog.dismissCallBack = {
            resumeAllPlayer()
        }
    }

    /**
     * Get Resource from Intent and Set to the Selected Screen
     */
    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val data = it.data
                data?.let { intentData ->
                    val url = intentData.getStringExtra(ConstantUtil.INTENT_VIDEO_URL).toString()
                    when (selectedScreen) {
                        1 -> {
                            ivFirstAdd.gone()
                            setupMedia(url, exoPlayerFirst)
                        }
                        2 -> {
                            ivSecondAdd.gone()
                            setupMedia(url, exoPlayerSecond)
                        }
                        3 -> {
                            ivThirdAdd.gone()
                            setupMedia(url, exoPlayerThird)
                        }
                        4 -> {
                            ivFourthAdd.gone()
                            setupMedia(url, exoPlayerFourth)
                        }
                    }
                }
            }
        }

    /**
     * Setup Focus Change Listeners
     */
    private fun setUpFocusChangeListeners() {
        binding.ivBack.requestFocus()

        lifecycleScope.launch {
            delay(500)
            binding.ivBack.requestFocus()

            /** Focus Change Listeners */
            focusChangeListeners(rlFirstGrid, exoPlayerFirst)
            focusChangeListeners(rlSecondGrid, exoPlayerSecond)
            focusChangeListeners(rlThirdGrid, exoPlayerThird)
            focusChangeListeners(rlFourthGrid, exoPlayerFourth)
        }

    }

    /**
     * Focus Change Listeners
     */
    private fun focusChangeListeners(selectedView: RelativeLayout, player: ExoPlayer) {
        selectedView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                player.volume = 1f
                selectedView.setBackgroundResource(R.drawable.select_bg_program_list)
            } else {
                player.volume = 0f
                selectedView.setBackgroundResource(R.drawable.transparent_bg)
            }
        }
    }

    /**
     * Pause Player
     */
    private fun pausePlayer(player: ExoPlayer) {
        player.playWhenReady = false
        player.playbackState
    }

    /**
     * Pause All Players
     */
    private fun pauseAllPlayers() {
        pausePlayer(exoPlayerFirst)
        pausePlayer(exoPlayerSecond)
        pausePlayer(exoPlayerThird)
        pausePlayer(exoPlayerFourth)
    }

    /**
     * Resume Player
     */
    private fun resumePlayer(player: ExoPlayer) {
        player.playWhenReady = true
        player.playbackState
    }

    /**
     * Resume All Players
     */
    private fun resumeAllPlayer() {
        resumePlayer(exoPlayerFirst)
        resumePlayer(exoPlayerSecond)
        resumePlayer(exoPlayerThird)
        resumePlayer(exoPlayerFourth)
    }

    override fun onResume() {
        super.onResume()
        resumeAllPlayer()
    }

    override fun onPause() {
        super.onPause()
        pauseAllPlayers()
    }

    /**
     * Release All Player
     */
    override fun onDestroy() {
        exoPlayerFirst.release()
        exoPlayerSecond.release()
        exoPlayerThird.release()
        exoPlayerFourth.release()
        super.onDestroy()
    }

}