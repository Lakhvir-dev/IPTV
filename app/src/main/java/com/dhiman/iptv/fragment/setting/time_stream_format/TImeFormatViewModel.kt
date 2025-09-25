package com.dhiman.iptv.fragment.setting.time_stream_format

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class TImeFormatViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)

    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("PP", R.drawable.privacy_policy, "Privacy Policy"),
            SettingItem("FEEDBACK", R.drawable.feedack, "Feedback"),
            SettingItem("TERMS", R.drawable.terms_and_condition, "Terms of use"),
        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "PP" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "FEEDBACK" -> {
            }

            "TERMS" -> {
            }
        }
    }


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