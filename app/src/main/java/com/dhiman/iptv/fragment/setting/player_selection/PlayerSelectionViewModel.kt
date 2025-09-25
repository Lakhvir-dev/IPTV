package com.dhiman.iptv.fragment.setting.player_selection

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class PlayerSelectionViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)
    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("LIVE_TV", R.drawable.tv_icon, "Live TV"),
            SettingItem("MOVIES", R.drawable.video_icon, "Movies"),
            SettingItem("SERIES", R.drawable.series_icon, "Series"),
            SettingItem("CATCH_UP", R.drawable.catch_up_icon, "Catch up"),
            SettingItem("REC", R.drawable.rec_icon, "Recording"),
            SettingItem("AUTO_CLEAR_CACHE", R.drawable.live_tv_icon, "Auto Clear Cache"),
        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "LIVE_TV" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "MOVIES" -> {
            }

            "SERIES" -> {
            }

            "CATCH_UP" -> {

            }

            "REC" -> {
            }

            "AUTO_CLEAR_CACHE" -> {
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