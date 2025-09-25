package com.dhiman.iptv.activity.series_program

import androidx.activity.viewModels
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.databinding.ActivitySeriesDetailsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SeriesDetailsActivity : BaseActivity<ActivitySeriesDetailsBinding>(
    ActivitySeriesDetailsBinding::inflate
) {
    private val viewModel: SeriesDetailsViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
    }
}