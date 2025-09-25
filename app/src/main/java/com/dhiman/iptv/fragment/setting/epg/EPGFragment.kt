package com.dhiman.iptv.fragment.setting.epg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentEPGBinding
import kotlin.getValue


class EPGFragment : Fragment() {
    lateinit var binding: FragmentEPGBinding
    private lateinit var adapter: EpgSettingsAdapter
    private val viewModel: EpgSettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEPGBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerViewData()

    }

    private fun setupRecyclerViewData() {
        adapter = EpgSettingsAdapter(mutableListOf()) { item ->
            val fragment =  when (item.key) {
                "TEST" -> {
                    // context.startActivity(Intent(context, MyPlayListActivity::class.java))
                }

                "TEST_1" -> {
                }

                "TEST_2" -> {
                }

                "TEST_3" -> {

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