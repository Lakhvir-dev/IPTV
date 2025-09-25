package com.dhiman.iptv.fragment.setting.epg

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class EpgSettingViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)
    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("TEST", R.drawable.restart_icon, "TEST"),
            SettingItem("TEST_1", R.drawable.epg_settings, "TEST"),
            SettingItem("TEST_2", R.drawable.cc_icon, "TEST"),
            SettingItem("TEST_3", R.drawable.autoplay_icon, "TEST"),

        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "TEST" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "TEST_1" -> {
            }

            "TEST_2" -> {
            }

            "TEST_3" -> {

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