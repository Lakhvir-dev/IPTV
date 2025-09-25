package com.dhiman.iptv.dialog.parental_control

import android.view.View
import androidx.databinding.ObservableField
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class ParentalControlViewModel : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }
        }
    }
}