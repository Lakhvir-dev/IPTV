package com.dhiman.iptv.dialog.automation

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.util.base.BaseViewModel

class AutomationViewModel : BaseViewModel() {
    val isEpgAutoUpdate = MutableLiveData<Boolean>()
    val isChannelMovieAutoUpdate = MutableLiveData<Boolean>()
    val isFocusOnBack = ObservableField(true)
    val isFocusOnChannel = ObservableField(false)
    val isFocusOnEpg = ObservableField(false)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }

            R.id.etAutoUpdateChannel -> {
                isFocusOnChannel.set(value)
            }

            R.id.etAutoUpdateEpg -> {
                isFocusOnEpg.set(value)
            }

        }

    }


    fun onClick(view: View) {
        when (view.id) {
            R.id.etAutoUpdateChannel -> {
                if (isChannelMovieAutoUpdate.value == true) {
                    isChannelMovieAutoUpdate.postValue(false)
                } else {
                    isChannelMovieAutoUpdate.postValue(true)
                }

                isFocusOnChannel.set(true)
                isFocusOnEpg.set(false)
            }

            R.id.etAutoUpdateEpg -> {
                if (isEpgAutoUpdate.value == true) {
                    isEpgAutoUpdate.postValue(false)
                } else {
                    isEpgAutoUpdate.postValue(true)
                }

                isFocusOnChannel.set(false)
                isFocusOnEpg.set(true)
            }
        }
    }
}