package com.dhiman.iptv.activity.setting.general_settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.ActivityGeneralSettingsBinding
import com.dhiman.iptv.fragment.setting.general_settings.GeneralSettingViewModel
import com.dhiman.iptv.fragment.setting.general_settings.GeneralSettingsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue
@AndroidEntryPoint
class GeneralSettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivityGeneralSettingsBinding
    private val viewModel: GeneralSettingViewModel by viewModels()
    private lateinit var adapter: GeneralSettingsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_general_settings
        )
        binding.viewModel = viewModel

    }

    override fun onResume() {
        super.onResume()
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        adapter = GeneralSettingsAdapter(mutableListOf()) { item ->
            viewModel.onItemClick(item, this)
        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(this) { list ->
            adapter.updateList(list)
        }
    }
}