package com.dhiman.iptv.fragment.setting.general_settings

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class PlaylistAgentViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)

    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("player_lite", R.drawable.check, "Player Lite"),
            SettingItem("player_lite_1", R.drawable.check, "Player Lite 1"),

        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "player_lite" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "player_lite_1" -> {
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