package com.dhiman.iptv.activity.splash

import android.annotation.SuppressLint
import android.content.Intent
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.login_type.LoginTypeActivity
import com.dhiman.iptv.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onActivityReady() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(2500)
            startActivity(Intent(this@SplashActivity, LoginTypeActivity::class.java))
            finish()
        }

    }

}