package com.dhiman.iptv.fragment.setting.general_settings

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class LanguageViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)

    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("HINDI", R.drawable.check, "Hindi"),
            SettingItem("ENGLISH", R.drawable.check, "English"),
            SettingItem("PUNJABI", R.drawable.check, "Punjabi"),
            SettingItem("TAMIL", R.drawable.check, "Tamil"),

            )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "HINDI" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "ENGLISH" -> {
            }

            "PUNJABI" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "TAMIL" -> {
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