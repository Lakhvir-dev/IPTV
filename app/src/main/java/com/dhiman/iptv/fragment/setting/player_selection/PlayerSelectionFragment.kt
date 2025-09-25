package com.dhiman.iptv.fragment.setting.player_selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentPlayerSelectionBinding
import kotlin.getValue


class PlayerSelectionFragment : Fragment() {
    lateinit var binding: FragmentPlayerSelectionBinding
    private val viewModel: PlayerSelectionViewModel by viewModels()
    private lateinit var adapter: PlayerSelectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlayerSelectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        adapter = PlayerSelectionAdapter(mutableListOf()) { item ->

        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(requireActivity()) { list ->
            adapter.updateList(list)
        }
    }
}