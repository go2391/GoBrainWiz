package brainwiz.gobrainwiz

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import brainwiz.gobrainwiz.api.ApiStringConstants


/**
 */
class SplashActivity : BaseActivity(), ApiStringConstants {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.splash)
/*
        Handler().postDelayed({
            launchActivity(this@SplashActivity as Activity, DashboardActivity::class.java, null, true, IAnimations.Companion.RightToLeft)
        }, 5000)*/

    }

}
