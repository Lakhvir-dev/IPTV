package com.dhiman.iptv.activity.login_type

import android.content.Intent
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.steam_login.SteamLoginActivity
import com.dhiman.iptv.databinding.ActivityLoginTypeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.jvm.java

@AndroidEntryPoint
class LoginTypeActivity :
    BaseActivity<ActivityLoginTypeBinding>(ActivityLoginTypeBinding::inflate) {
    override fun onActivityReady() {
        binding.apply {
            streamLogin.setOnClickListener {
                startActivity(Intent(this@LoginTypeActivity, SteamLoginActivity::class.java))
            }
        }
    }

}