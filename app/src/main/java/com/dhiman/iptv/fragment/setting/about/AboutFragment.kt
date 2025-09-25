package com.dhiman.iptv.fragment.setting.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dhiman.iptv.databinding.FragmentAboutBinding
import kotlin.getValue

class AboutFragment : Fragment() {
    lateinit var binding: FragmentAboutBinding
    private val viewModel: AboutViewModel by viewModels()
    private lateinit var adapter: AboutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setupRecyclerViewData()
    }

    private fun setupRecyclerViewData() {
        adapter = AboutAdapter(mutableListOf()) { item ->
            when (item.key) {
                "PP" -> {
                    // context.startActivity(Intent(context, MyPlayListActivity::class.java))
                }

                "FEEDBACK" -> {
                }

                "TERMS" -> {
                }
            }
        }
        binding.recyclerSettings.adapter = adapter

        // Observe list
        viewModel.settingItems.observe(requireActivity()) { list ->
            adapter.updateList(list)
        }
    }

}