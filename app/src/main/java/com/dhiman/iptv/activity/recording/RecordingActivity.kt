package com.dhiman.iptv.activity.recording

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.dhiman.iptv.R
import com.dhiman.iptv.databinding.ActivityRecordingBinding

class RecordingActivity : AppCompatActivity() {

    private val viewModel: RecordingListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRecordingBinding>(
            this,
            R.layout.activity_recording
        )
        binding.viewModel = viewModel
    }

}