package com.dhiman.iptv.dialog.time_format

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class TimeFormatViewModel : BaseViewModel() {

    val is24Hours = MutableLiveData<Boolean>()
    val isFocusOnBack = ObservableField(true)
    val isFocusOn24Hour = ObservableField(false)
    val isFocusOn12Hour = ObservableField(false)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }

            R.id.et24Hour -> {
                isFocusOn24Hour.set(value)
            }

            R.id.et12Hour -> {
                isFocusOn12Hour.set(value)
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
//            R.id.hours24TextView -> {
//                is24Hours.postValue(true)
//            }
//
//            R.id.hours12TextView -> {
//                is24Hours.postValue(false)
//            }

            R.id.et24Hour -> {
                is24Hours.postValue(true)
                isFocusOn24Hour.set(true)
                isFocusOn12Hour.set(false)
            }

            R.id.et12Hour -> {
                is24Hours.postValue(false)
                isFocusOn24Hour.set(false)
                isFocusOn12Hour.set(true)
            }

//            R.id.til24Hour -> {
//                is24Hours.postValue(true)
//                isFocusOn24Hour.set(true)
//                isFocusOn12Hour.set(false)
//            }
//
//            R.id.til12Hour -> {
//                is24Hours.postValue(false)
//                isFocusOn24Hour.set(false)
//                isFocusOn12Hour.set(true)
//            }
        }
    }
}