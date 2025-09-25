package com.dhiman.iptv.fragment.setting.time_stream_format

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentStreamFormatBinding
import kotlin.getValue


class StreamFormatFragment : Fragment() {
    lateinit var binding: FragmentStreamFormatBinding
    private lateinit var adapter: StreamFormatAdapter
    private val viewModel: StreamFormatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamFormatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        adapter = StreamFormatAdapter(mutableListOf()) { item ->
            val fragment =  when (item.key) {
                "HLS" -> {
                    // context.startActivity(Intent(context, MyPlayListActivity::class.java))
                }

                "MPEG" -> {
                }

                "MP4" -> {
                }

                else -> {}
            }
        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(requireActivity()) { list ->
            adapter.updateList(list)
        }
    }

}