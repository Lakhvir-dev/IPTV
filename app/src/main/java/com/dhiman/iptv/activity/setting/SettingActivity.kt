package com.dhiman.iptv.activity.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.ActivitySettingBinding
import com.dhiman.iptv.fragment.setting.AutomationFragment
import com.dhiman.iptv.fragment.setting.ChangeThemeFragment
import com.dhiman.iptv.fragment.setting.SpeedTestFragment
import com.dhiman.iptv.fragment.setting.about.AboutFragment
import com.dhiman.iptv.fragment.setting.backup_restore.BackupFragment
import com.dhiman.iptv.fragment.setting.epg.EPGFragment
import com.dhiman.iptv.fragment.setting.external_player.ExternalPlayerFragment
import com.dhiman.iptv.fragment.setting.general_settings.GeneralSettingFragment
import com.dhiman.iptv.fragment.setting.parental_control.ParentalControlFragment
import com.dhiman.iptv.fragment.setting.player_selection.PlayerSelectionFragment
import com.dhiman.iptv.fragment.setting.player_setting.PlayerSettingsFragment
import com.dhiman.iptv.fragment.setting.subtitleSetting.SubtitleFragment
import com.dhiman.iptv.fragment.setting.time_stream_format.StreamFormatFragment
import com.dhiman.iptv.fragment.setting.time_stream_format.TimeFormatFragment
import com.dhiman.iptv.util.ConstantUtil
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private lateinit var adapter: SettingsAdapter
    private val viewModel:
            SettingViewModel by viewModels()
    lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_setting
        )
        binding.viewModel = viewModel

        /** Observer Listeners */
        setupObserver()

        /** Set up onFocus Change Listeners */
        setUpFocusChangeListeners()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_right, GeneralSettingFragment())
            .commit()
    }

    /**
     * Set up onFocus Change Listeners
     */
    private fun setUpFocusChangeListeners() {
//        binding.ivBack.requestFocus()
//        Handler(Looper.getMainLooper()).postDelayed({
//            binding.ivBack.requestFocus()
//        }, 500)
    }

    /**
     * Observer Listeners
     */
    private fun setupObserver() {
        viewModel.checkDateTimePreferences.observe(this) {
            if (it) {
                updateDateTimePreferences()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        /** Update Date & Time According to User Preferences */
        updateDateTimePreferences()

        setupRecyclerViewData()

    }

    private fun setupRecyclerViewData() {
        adapter = SettingsAdapter(mutableListOf()) { item ->
            val fragment = when (item.key) {
                "GENERAL_SETTINGS" -> {
                    GeneralSettingFragment()
                }

                "EPG" -> {
                    EPGFragment()
                }

                "STREAM_FORMAT" -> {
                    StreamFormatFragment()
                }

                "TIME_FORMAT" -> {
                    TimeFormatFragment()
                    /*  val dialog = TimeFormatDialog()
                      dialog.show(context.supportFragmentManager, "")
                      dialog.callBack = {
                          // your callback logic
                      }*/
                }

                "PLAYER_SETTINGS" -> {
                    PlayerSettingsFragment()
                }


                "PARENT_CONTROL" -> {
                    ParentalControlFragment()
                }

                "PLAYER_SELECTION" -> {
                    PlayerSelectionFragment()
                }

                "EXTERNAL_PLAYER" -> {
                    ExternalPlayerFragment()
                }

                "AUTOMATION" -> {
                    AutomationFragment()
                }

                "SPEED_TEST" -> {
                    SpeedTestFragment()
                }

                "CHANGE_THEME" -> {
                    ChangeThemeFragment()
                }

                "BACKUP" -> {
                    BackupFragment()
                }

                "SUBTITLE" -> {
                    SubtitleFragment()
                }

                "ABOUT" -> {
                    AboutFragment()
                }

                else -> {}
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_right, fragment as Fragment)
                .commit()
        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(this) { list ->
            adapter.updateList(list)
        }
    }

    /**
     * Update Date & Time According to User Preferences
     */
    private fun updateDateTimePreferences() {
        val is24HourFormat = sharedPrefs.getBoolean(ConstantUtil.IS_24_HOURS_FORMAT)
        if (is24HourFormat) {
            binding.tvTime.format24Hour = "HH:mm"
            binding.tvTime.format12Hour = "HH:mm"
            binding.tvTimeFormat.visibility = View.INVISIBLE
        } else {
            binding.tvTime.format24Hour = "hh:mm"
            binding.tvTime.format12Hour = "hh:mm"
            binding.tvTimeFormat.visibility = View.VISIBLE
        }
    }

}