package com.dhiman.iptv.fragment.setting.external_player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentExternalPlayerBinding
import kotlin.getValue

class ExternalPlayerFragment : Fragment() {
    lateinit var binding: FragmentExternalPlayerBinding
    private val viewModel: ExternalPlayerViewModel by viewModels()
    private lateinit var adapter: ExternalPlayerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExternalPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerViewData()
    }


    private fun setupRecyclerViewData() {
        adapter = ExternalPlayerAdapter(mutableListOf()) { item ->
          val fragment =   when (item.key) {
                "VIDEO" -> {
                    // context.startActivity(Intent(context, MyPlayListActivity::class.java))
                }

                "PHOTOS" -> {
                }

                "VLC" -> {
                }

                "ZOOM" -> {
                }

                "VIDEO_DOWNLOADER" -> {
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