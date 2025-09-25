package com.dhiman.iptv.activity.steam_login

import android.content.Intent
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.dashboard.HomeActivity
import com.dhiman.iptv.activity.user_list.UserListActivity
import com.dhiman.iptv.databinding.ActivitySteamLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SteamLoginActivity :
    BaseActivity<ActivitySteamLoginBinding>(ActivitySteamLoginBinding::inflate) {
    override fun onActivityReady() {
        binding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@SteamLoginActivity, HomeActivity::class.java))
            }

            llUserList.setOnClickListener {
                startActivity(Intent(this@SteamLoginActivity, UserListActivity::class.java))
            }
        }

    }

}