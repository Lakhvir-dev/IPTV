package com.dhiman.iptv.fragment.setting.general_settings

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.util.base.BaseViewModel

class AutoPlayNextViewModel : BaseViewModel() {
    val settingItems = MutableLiveData<List<SettingItem>>()
    val isFocusOnBack = ObservableField(true)

    init {
        loadSettings()
    }


    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("10", R.drawable.check, "10s"),
            SettingItem("20", R.drawable.check, "20s"),
            SettingItem("30", R.drawable.check, "30s"),
            SettingItem("40", R.drawable.check, "40s"),
            SettingItem("50", R.drawable.check, "50s"),
            SettingItem("60", R.drawable.check, "60s"),
        )
    }


    fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "10" -> {
                // context.startActivity(Intent(context, MyPlayListActivity::class.java))
            }

            "20" -> {
            }

            "30" -> {
            }

            "40" -> {
            }

            "50" -> {
            }

            "60" -> {
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