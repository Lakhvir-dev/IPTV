package com.dhiman.iptv.fragment.setting.external_player

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class ExternalPlayerViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)

    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("VIDEO", R.drawable.ic_video_player, "Video"),
            SettingItem("PHOTOS", R.drawable.ic_google_player, "Photos"),
            SettingItem("VLC", R.drawable.vlc, "VLC"),
            SettingItem("ZOOM", R.drawable.zoom_player, "Zoom"),
            SettingItem("VIDEO_DOWNLOADER", R.drawable.ic_downloder, "Video Downloader"),
        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "VIDEO" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "PHOTOS" -> {
            }

            "VLC" -> {
            }

            "ZOOM" -> {
            }

            "VIDEO_DOWNLOADER" -> {
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