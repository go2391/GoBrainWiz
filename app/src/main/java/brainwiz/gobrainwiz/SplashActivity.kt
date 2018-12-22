package brainwiz.gobrainwiz

import android.app.Activity
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.VideoView
import brainwiz.gobrainwiz.api.ApiStringConstants


/**
 */
class SplashActivity : BaseActivity(), ApiStringConstants {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        playVideo()
/*
        Handler().postDelayed({
            launchActivity(this@SplashActivity as Activity, DashboardActivity::class.java, null, true, IAnimations.Companion.RightToLeft)
        }, 5000)*/

    }

    private fun playVideo() {
        var videoView = findViewById<VideoView>(R.id.splash_video_view)
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.demo))
        videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { m ->
            var m = m
            try {
                if (m.isPlaying) {
                    m.stop()
                    m.release()
                    m = MediaPlayer()
                }
                m.isLooping = false
                m.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

}
