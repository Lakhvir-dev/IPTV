package com.dhiman.iptv.fragment.setting.general_settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentPreferredLanguageBinding
import kotlin.getValue

class PreferredLanguageFragment : Fragment() {
    lateinit var binding: FragmentPreferredLanguageBinding
    private lateinit var adapter: LanguageAdapter
    private val viewModel: LanguageViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPreferredLanguageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        adapter = LanguageAdapter(mutableListOf()) { item ->

        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(requireActivity()) { list ->
            adapter.updateList(list)
        }
    }

}