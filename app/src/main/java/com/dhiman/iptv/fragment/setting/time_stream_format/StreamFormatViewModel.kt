package com.dhiman.iptv.fragment.setting.time_stream_format

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class StreamFormatViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)

    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("HLS", R.drawable.check, "HLS (.m3u8)"),
            SettingItem("MPEG", R.drawable.check, "MPEG-DASH"),
            SettingItem("MP4", R.drawable.check, "MP4")
        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "HLS" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "MPEG" -> {
            }

            "MP4" -> {
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