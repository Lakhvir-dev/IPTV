package com.dhiman.iptv.activity.catch_up

import androidx.activity.viewModels
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.databinding.ActivityCatchUpItemsListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatchUpItemsListActivity :
    BaseActivity<ActivityCatchUpItemsListBinding>(ActivityCatchUpItemsListBinding::inflate) {

    private val viewModel: CatchUpItemsListViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
    }
}