package com.dhiman.iptv.activity.youtubePlayer

import android.os.Bundle
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.BaseOldActivity
import com.dhiman.iptv.util.ConstantUtil
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubePlayerActivity : BaseOldActivity() {

    //    private var youTubeView: YouTubePlayerView? = null
//    private var youTubePlayer: YouTubePlayer? = null
    private var videoId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_player)


        /** View Initialize & Data Setup */
        initView()
        //  youTubeView!!.initialize(ConstantUtil.YOUTUBE_API_KEY, this)
    }

    /**
     * View Initialize & Data Setup
     */
    private fun initView() {
        //  youTubeView = findViewById(R.id.youtube_view)
        videoId = intent.getStringExtra(ConstantUtil.INTENT_ID)

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId.toString(), 0f)
            }
        })

    }

//    override fun onInitializationSuccess(
//        provider: YouTubePlayer.Provider,
//        youTubePlayer: YouTubePlayer,
//        wasRestored: Boolean
//    ) {
//        if (!wasRestored) {
//            this.youTubePlayer = youTubePlayer
//            youTubePlayer.setPlayerStateChangeListener(this)
//            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
//            if (videoId != null) {
//                youTubePlayer.cueVideo(videoId)
//            }
//
//        }
//
//    }
//
//    override fun onInitializationFailure(provider: YouTubePlayer.Provider, errorReason: YouTubeInitializationResult) {
//        if (errorReason.isUserRecoverableError) {
//            // error is recoverable by user action
//            errorReason.getErrorDialog(this, ConstantUtil.RECOVERY_REQUEST)
//        } else {
//            // error is not recoverable by user action
//            val error = String.format(getString(R.string.error_player), errorReason.toString())
//            Toast.makeText(this@YoutubePlayerActivity, error, Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        if (requestCode == ConstantUtil.RECOVERY_REQUEST) {
//            youTubeView!!.initialize(ConstantUtil.YOUTUBE_API_KEY, this)
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onLoading() {
//
//    }
//
//    override fun onLoaded(s: String) {
//        youTubePlayer!!.play()
//    }
//
//    override fun onAdStarted() {
//
//    }
//
//    override fun onVideoStarted() {
//
//    }
//
//    override fun onVideoEnded() {
//
//    }
//
//    override fun onError(errorReason: YouTubePlayer.ErrorReason) {
//
//    }

}
