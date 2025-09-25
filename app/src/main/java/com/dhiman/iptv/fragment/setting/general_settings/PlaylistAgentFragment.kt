package com.dhiman.iptv.fragment.setting.general_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentPlaylistAgentBinding
import kotlin.getValue

class PlaylistAgentFragment : Fragment() {
    lateinit var binding: FragmentPlaylistAgentBinding
    private lateinit var adapter: PlaylistAgentAdapter
    private val viewModel: PlaylistAgentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistAgentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        adapter = PlaylistAgentAdapter(mutableListOf()) { item ->

        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(requireActivity()) { list ->
            adapter.updateList(list)
        }
    }


}