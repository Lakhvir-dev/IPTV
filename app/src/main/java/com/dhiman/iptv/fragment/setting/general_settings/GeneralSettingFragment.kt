package com.dhiman.iptv.fragment.setting.general_settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentGeneralSettingBinding
import kotlin.getValue

class GeneralSettingFragment : Fragment() {
    lateinit var binding: FragmentGeneralSettingBinding
    private lateinit var adapter: GeneralSettingsAdapter
    private val viewModel: GeneralSettingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        adapter = GeneralSettingsAdapter(mutableListOf()) { item ->
            val fragment =  when (item.key) {
                "BOOTUP" -> {
                     requireActivity().startActivity(Intent(context, GeneralSettingsActivity::class.java))
                }

                "FULL_EPG" -> {
                }

                "ACTIVE_SUBTITLE" -> {
                }

                "ANEXT_EPISODE" -> {

                }

                "PIP" -> {
                }


                "CLEAR_CACHE" -> {

                }

                "CHANNEL_LIST" -> {

                }

                "PLAYLIST_AGENT" -> {
                }

                "PREFERRED" -> {

                }

                else -> {}
            }

//            requireActivity().supportFragmentManager.beginTransaction()
//                .replace(R.id.fragment_right, fragment as Fragment)
//                .commit()
        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(requireActivity()) { list ->
            adapter.updateList(list)
        }
    }


}