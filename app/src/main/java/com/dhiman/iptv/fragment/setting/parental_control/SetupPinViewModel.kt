package com.dhiman.iptv.fragment.setting.parental_control

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class SetupPinViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)

    val isFocusOnDisablePin = ObservableField(true)
    val isFocusOnSetupPin = ObservableField(true)
    val isFocusOnChangePin = ObservableField(true)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }

            R.id.changePinLayout -> {

            }

            R.id.setupPinLayout -> {
                isFocusOnSetupPin.set(value)
            }

            R.id.disablePinLayout -> {

            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }

            R.id.setupPinLayout -> {
                view.context.apply {
                    startActivity(Intent(this, SetupPinActivity::class.java))
                }
            }
        }
    }
}