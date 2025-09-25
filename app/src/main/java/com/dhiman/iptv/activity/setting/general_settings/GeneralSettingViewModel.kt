package com.dhiman.iptv.activity.setting.general_settings

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class GeneralSettingViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()

    init {
        loadSettings()
    }

    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("BOOTUP", R.drawable.restart_icon, "AutoStart on Bootup"),
            SettingItem("FULL_EPG", R.drawable.epg_settings, "Show Full EPG"),
            SettingItem("ACTIVE_SUBTITLE", R.drawable.cc_icon, "Active Subtitle"),
            SettingItem("ANEXT_EPISODE", R.drawable.autoplay_icon, "Autoplay Next Episode in"),
            SettingItem("PIP", R.drawable.picture_in_picture_icon, "Picture-in-picture"),
            SettingItem("CLEAR_CACHE", R.drawable.clear_cache, "Auto Clear Cache"),
            SettingItem("CHANNEL_LIST", R.drawable.channel_list_icon, "Show EPG in channel List"),
            SettingItem("PLAYLIST_AGENT", R.drawable.playlist_agenda, "Set Playlist Agent"),
            SettingItem("PREFERRED", R.drawable.language, "Set Your Preferred Language"),
        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "BOOTUP" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "FULL_EPG" -> {
            }

            "ACTIVE_SUBTITLE" -> {
            }

            "ANEXT_EPISODE" -> {

            }

            "PIP" -> {
            }


            "CLEAR_CACHE" -> {

            }

            "CHANNEL_LIST" -> {

            }

            "PLAYLIST_AGENT" -> {
            }

            "PREFERRED" -> {

            }
        }
    }
}