package com.dhiman.iptv.dialog.logout

import android.view.View
import androidx.databinding.ObservableField
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class LogoutConfirmationViewModel : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }
        }
    }

}