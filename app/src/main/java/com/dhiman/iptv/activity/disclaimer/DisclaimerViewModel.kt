package com.dhiman.iptv.activity.disclaimer

import android.content.Intent
import android.view.View
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.login_type.LoginTypeActivity
import com.dhiman.iptv.util.base.BaseViewModel

class DisclaimerViewModel : BaseViewModel() {

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnContinue -> {
                view.context.apply {
                    startActivity(Intent(this, LoginTypeActivity::class.java))
                }
            }
        }
    }
}