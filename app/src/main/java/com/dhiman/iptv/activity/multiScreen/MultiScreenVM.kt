package com.dhiman.iptv.activity.multiScreen

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class MultiScreenVM : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }
        }
    }

}