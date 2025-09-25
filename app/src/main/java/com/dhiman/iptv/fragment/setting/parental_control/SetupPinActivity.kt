package com.dhiman.iptv.fragment.setting.parental_control

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.ActivitySetupPinBinding
import kotlin.getValue

class SetupPinActivity : AppCompatActivity() {
    lateinit var binding: ActivitySetupPinBinding
    private val viewModel: SetupPinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_setup_pin
        )
        binding.viewModel = viewModel
    }
}