package com.dhiman.iptv.activity.disclaimer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.ActivityDisclaimerBinding

class DisclaimerActivity : AppCompatActivity() {

    private val viewModel: DisclaimerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityDisclaimerBinding>(
            this,
            R.layout.activity_disclaimer
        )
        binding.viewModel = viewModel
    }
}