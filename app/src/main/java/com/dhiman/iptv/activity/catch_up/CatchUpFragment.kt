package com.dhiman.iptv.activity.catch_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiman.iptv.activity.live_program_list.adapter.ChannelUnderCategoryAdapter
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.data.model.ChannelUnderCategoryModel
import com.dhiman.iptv.databinding.ListItemCatchUpChannelBinding

class CatchUpFragment: Fragment() {

    var binding: ListItemCatchUpChannelBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding= ListItemCatchUpChannelBinding.inflate(LayoutInflater.from(container?.context))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCatchUpAdapter()
        channelUnderCategoryAdapter()
    }

    fun setCatchUpAdapter(){
        val list=arrayListOf(
            LiveCategoryModel("1","All"),
            LiveCategoryModel("1","News"),
            LiveCategoryModel("1","Discovery"),
            LiveCategoryModel("1","Sports"),
            LiveCategoryModel("1","Movies"),
            LiveCategoryModel("1","Serials"),
            LiveCategoryModel("1","Series"),
        )
        val adapter=CatchUpAdapter(list)
        binding?.recyclerCategories?.adapter=adapter
    }


    /**
     * Recycler View Name Adapter
     */
    private fun channelUnderCategoryAdapter() {
        val channelsList = listOf(
            ChannelUnderCategoryModel(
                channelName = "CNN Channel",
                channelDesc = "Program info Entertainment",
                ivLogo = "cnn_logo.png",
                ivLike = "liked"
            ),
            ChannelUnderCategoryModel(
                channelName = "CNN Channel",
                channelDesc = "Program info Entertainment",
                ivLogo = "cnn_logo.png",
                ivLike = "unliked"
            ),
            ChannelUnderCategoryModel(
                channelName = "ESPN Channel",
                channelDesc = "Program info Entertainment",
                ivLogo = "espn_logo.png",
                ivLike = "liked"
            ),
            ChannelUnderCategoryModel(
                channelName = "CNN Channel",
                channelDesc = "Program info Entertainment",
                ivLogo = "cnn_logo.png",
                ivLike = "unliked"
            ),
            ChannelUnderCategoryModel(
                channelName = "CNN Channel",
                channelDesc = "Program info Entertainment",
                ivLogo = "cnn_logo.png",
                ivLike = "liked"
            ),
            ChannelUnderCategoryModel(
                channelName = "CNN Channel",
                channelDesc = "Program info Entertainment",
                ivLogo = "cnn_logo.png",
                ivLike = "unliked"
            ),
            ChannelUnderCategoryModel(
                channelName = "CNN Channel",
                channelDesc = "Program info Entertainment",
                ivLogo = "cnn_logo.png",
                ivLike = "unliked"
            )
        )


        val mLayoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerChannels?.layoutManager = mLayoutManager

       val  channelUnderCategoryAdapter = ChannelUnderCategoryAdapter(channelsList) {

        }
        binding?.recyclerChannels?.adapter = channelUnderCategoryAdapter
    }
}