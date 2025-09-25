package com.dhiman.iptv.activity.setting

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.multiScreenGrid.MultiScreenGridActivity
import com.dhiman.iptv.activity.my_play_list.MyPlayListActivity
import com.dhiman.iptv.data.model.SettingItem
import com.dhiman.iptv.dialog.automation.AutomationDialog
import com.dhiman.iptv.dialog.parental_control.ParentalControlDialog
import com.dhiman.iptv.dialog.time_format.TimeFormatDialog
import com.dhiman.iptv.util.base.BaseViewModel

class SettingViewModel : BaseViewModel() {

    val checkDateTimePreferences = MutableLiveData<Boolean>()
    val isFocusOnBack = ObservableField(true)
    val isFocusMyPlaylist = ObservableField(false)
    val isFocusTimeFormat = ObservableField(false)
    val isFocusParentalControl = ObservableField(false)
    val isFocusMultiScreenGrid = ObservableField(false)
    val isFocusAutomation = ObservableField(false)


    // LiveData for RecyclerView list
    val settingItems = MutableLiveData<List<SettingItem>>()


    init {
        loadSettings()
    }

    private fun loadSettings() {
        settingItems.value = listOf(
            SettingItem("GENERAL_SETTINGS", R.drawable.general_settings, "General Settings"),
            SettingItem("EPG", R.drawable.epg, "EPG"),
            SettingItem("STREAM_FORMAT", R.drawable.streaming_format, "Stream Format"),
            SettingItem("TIME_FORMAT", R.drawable.time_format, "Time Format"),
            SettingItem("PLAYER_SETTINGS", R.drawable.video_player, "Player Settings"),
            SettingItem("PARENT_CONTROL", R.drawable.parent_control, "Parent Control"),
            SettingItem("PLAYER_SELECTION", R.drawable.player_selection, "Player Selection"),
            SettingItem("EXTERNAL_PLAYER", R.drawable.exernal_player, "External Player"),
            SettingItem("AUTOMATION", R.drawable.automation_iconm, "Automation"),
            SettingItem("SPEED_TEST", R.drawable.speed, "Speed Test"),
            SettingItem("CHANGE_THEME", R.drawable.theme_icon, "Change Theme"),
            SettingItem("BACKUP", R.drawable.backup, "Backup and Restore"),
            SettingItem("SUBTITLE", R.drawable.subtitle, "Subtitle"),
            SettingItem("ABOUT", R.drawable.info_about, "About")
        )
    }


    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }

            R.id.clMyPlayList -> {
                isFocusMyPlaylist.set(value)
            }

            R.id.clTimeFormat -> {
                isFocusTimeFormat.set(value)
            }

            R.id.clParentalControl -> {
                isFocusParentalControl.set(value)
            }

            R.id.clAutomation -> {
                isFocusAutomation.set(value)
            }

            R.id.clMultiScreenGrid -> {
                isFocusMultiScreenGrid.set(value)
            }
        }

    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }

            R.id.clMyPlayList -> {
                view.context.apply {
                    startActivity(Intent(this, MyPlayListActivity::class.java))
                }
            }

            R.id.clMultiScreenGrid -> {
                view.context.apply {
                    startActivity(Intent(this, MultiScreenGridActivity::class.java))
                }
            }

            R.id.clTimeFormat -> {
                view.context.apply {
                    val timeFormatDialog = TimeFormatDialog()
                    timeFormatDialog.show((this as AppCompatActivity).supportFragmentManager, "")
                    timeFormatDialog.callBack = {
                        checkDateTimePreferences.postValue(true)
                    }
                }
            }

            R.id.clParentalControl -> {
                view.context.apply {
                    val parentalControlDialog = ParentalControlDialog()
                    parentalControlDialog.show(
                        (this as AppCompatActivity).supportFragmentManager,
                        ""
                    )
                    parentalControlDialog.callBack = {
                        // startActivity(Intent(this, UserListActivity::class.java))
                    }
                }
            }

            R.id.clAutomation -> {
                view.context.apply {
                    val automationDialog = AutomationDialog()
                    automationDialog.show(
                        (this as AppCompatActivity).supportFragmentManager,
                        ""
                    )
                    automationDialog.callBack = {
                        // startActivity(Intent(this, UserListActivity::class.java))
                    }
                }
            }
        }
    }


    /*fun onItemClick(item: SettingItem, context: AppCompatActivity) {
        when (item.key) {
            "GENERAL_SETTINGS" -> {
                 context.startActivity(Intent(context, GeneralSettingsActivity::class.java))
            }

            "EPG" -> {
                context.startActivity(Intent(context, EpgSettingActivity::class.java))
            }

            "STREAM_FORMAT" -> {
                context.startActivity(Intent(context, StreamFormatActivity::class.java))
            }

            "TIME_FORMAT" -> {
                context.startActivity(Intent(context, TimeFormatActivity::class.java))

                *//*  val dialog = TimeFormatDialog()
                  dialog.show(context.supportFragmentManager, "")
                  dialog.callBack = {
                      // your callback logic
                  }*//*
            }

            "PLAYER_SETTINGS" -> {
                context.startActivity(Intent(context, PlayerSettingsActivity::class.java))
            }


            "PARENTAL_CONTROL" -> {
                context.startActivity(Intent(context, ParentalControlActivity::class.java))

//                val dialog = ParentalControlDialog()
//                dialog.show(context.supportFragmentManager, "")
            }

            "PLAYER_SELECTION" -> {
                context.startActivity(Intent(context, PlayerSelectionActivity::class.java))
            }

            "EXTERNAL_PLAYER" -> {
                context.startActivity(Intent(context, ExternalPlayerActivity::class.java))
            }

            "AUTOMATION" -> {
                //context.startActivity(Intent(context, AutomationActivity::class.java))
                val dialog = AutomationDialog()
                dialog.show(context.supportFragmentManager, "")
            }

            "SPEED_TEST" -> {
                //context.startActivity(Intent(context, SpeedTestActivity::class.java))
            }

            "CHANGE_THEME" -> {
                //context.startActivity(Intent(context, ThemeActivity::class.java))
            }

            "BACKUP" -> {
                context.startActivity(Intent(context, BackupAndRestoreActivity::class.java))
            }

            "SUBTITLE" -> {
                context.startActivity(Intent(context, SubtitleActivity::class.java))
            }

            "ABOUT" -> {
                context.startActivity(Intent(context, AboutActivity::class.java))

            }
        }
    }*/
}