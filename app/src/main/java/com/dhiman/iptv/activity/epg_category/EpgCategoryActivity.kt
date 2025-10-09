package com.dhiman.iptv.activity.epg_category

import android.content.Context
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.live_program_list.LiveProgramNameListNewAdapter
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.databinding.ActivityEpgCategoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpgCategoryActivity :
    BaseActivity<ActivityEpgCategoryBinding>(ActivityEpgCategoryBinding::inflate) {

        var selectedPosition=-1
    private val viewModel: EpgListViewModel by viewModels()

    override fun onActivityReady() {
        binding.viewModel = viewModel
        setTimeAdapter()
        manageCategoryAdapter()
    }

    fun setTimeAdapter(){
        // Timeline setup
        val times = listOf("12:00 PM", "12:30 PM", "01:00 PM", "01:30 PM", "02:00 PM")
        binding.timelineRv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.timelineRv.adapter = TimelineAdapter(times)

        // Sample channels
        val samplePrograms = listOf(
            Program("Gotham", 0, 60),
            Program("News", 60, 120),
            Program("Sports", 120, 180)
        )
        val channels = listOf(
            Channel("FOX", R.drawable.ic_launcher_foreground, samplePrograms),
            Channel("CNN", R.drawable.ic_launcher_foreground, samplePrograms),
            Channel("HBO", R.drawable.ic_launcher_foreground, samplePrograms),
            Channel("ESPN", R.drawable.ic_launcher_foreground, samplePrograms)
        )

        binding.channelProgramRv.layoutManager = LinearLayoutManager(this)
        binding.channelProgramRv.adapter = ChannelAdapter(channels)

        // Position timeline indicator (example: at 1:15 PM)
        val minutesNow = 75 // 1:15 PM → 75 minutes after 12:00
        val pxOffset = dpToPx(this, minutesNow * ProgramAdapter.MINUTE_WIDTH_DP)
        binding.timeLineIndicator.translationX = pxOffset.toFloat()
    }

    private fun manageCategoryAdapter() {
        val programNameList = arrayListOf(
            LiveCategoryModel(categoryId = "1", categoryName = "Favorite Channels"),
            LiveCategoryModel(categoryId = "2", categoryName = "Channels History"),
            LiveCategoryModel(categoryId = "3", categoryName = "English Channels"),
            LiveCategoryModel(categoryId = "4", categoryName = "Sports Channels"),
            LiveCategoryModel(categoryId = "5", categoryName = "French Channels"),
            LiveCategoryModel(categoryId = "6", categoryName = "World Cricket"),
            LiveCategoryModel(categoryId = "7", categoryName = "Korean Channels"),
            LiveCategoryModel(categoryId = "8", categoryName = "News Channels"),
            LiveCategoryModel(categoryId = "9", categoryName = "Kids Channels")
        )

        val mLayoutManager = LinearLayoutManager(this)
        binding.rvProgramNameList.layoutManager = mLayoutManager

       val  programNameAdapter = LiveProgramNameListNewAdapter(programNameList) { pos ->
            selectedPosition = pos
            binding.rvProgramNameList.smoothScrollToPosition(pos)
            //fetchAllVideoStream()
        }
        binding.rvProgramNameList.adapter = programNameAdapter
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }
}