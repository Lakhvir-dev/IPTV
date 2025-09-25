package com.dhiman.iptv.fragment.setting.general_settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.ActivityGeneralSettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class GeneralSettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivityGeneralSettingsBinding
    private val viewModel: GeneralSettingViewModel by viewModels()
    private lateinit var adapter: GeneralAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_general_settings
        )
        binding.viewModel = viewModel
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_right, AutoStartBootupFragment())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        adapter = GeneralAdapter(mutableListOf()) { item ->
            val fragment = when (item.key) {
                "BOOTUP" -> {
                    AutoStartBootupFragment()
                }

                "FULL_EPG" -> {
                    ShowFullEPGFragment()
                }

                "ACTIVE_SUBTITLE" -> {
                    ActiveSubtitleFragment()
                }

                "ANEXT_EPISODE" -> {
                    AutoplayNextFragment()
                }

                "PIP" -> {
                    PIPFragment()
                }


                "CLEAR_CACHE" -> {
                    AutoClearCacheFragment()
                }

                "CHANNEL_LIST" -> {
                    EPGChannelListFragment()
                }

                "PLAYLIST_AGENT" -> {
                    PlaylistAgentFragment()
                }

                "PREFERRED" -> {
                    PreferredLanguageFragment()
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
}