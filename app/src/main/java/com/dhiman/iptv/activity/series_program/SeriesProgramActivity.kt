package com.dhiman.iptv.activity.series_program

import androidx.activity.viewModels
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.databinding.ActivitySeriesProgramBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesProgramActivity : BaseActivity<ActivitySeriesProgramBinding>(
    ActivitySeriesProgramBinding::inflate
) {
    private val viewModel: SeriesProgramListViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
    }
}