package com.dhiman.iptv.activity.live_program_list

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhiman.iptv.activity.BaseActivity
import com.dhiman.iptv.activity.live_program_list.adapter.ChannelUnderCategoryAdapter
import com.dhiman.iptv.activity.live_program_list.adapter.DateAdapter
import com.dhiman.iptv.activity.live_program_list.adapter.ScheduleAdapter
import com.dhiman.iptv.activity.player.PlayerActivity
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.ChannelUnderCategoryModel
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.databinding.ActivityLiveListNewBinding
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.ProgressDialogPref
import com.dhiman.iptv.util.RecyclerViewClickListener
import com.dhiman.iptv.util.gone
import com.dhiman.iptv.util.hideKeyboard
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LiveProgramListNewActivity :
    BaseActivity<ActivityLiveListNewBinding>(ActivityLiveListNewBinding::inflate),
    RecyclerViewClickListener {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: ListProgramListNewViewModel by viewModels()
    private lateinit var programListPaginationAdapter: LiveProgramListViewPagingAdapter
    private lateinit var programGridPaginationAdapter: LiveProgramGridViewPagingAdapter
    private lateinit var programNameAdapter: LiveProgramNameListNewAdapter

    private lateinit var channelUnderCategoryAdapter: ChannelUnderCategoryAdapter
    private lateinit var dateAdapter: DateAdapter
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var programNameList: ArrayList<LiveCategoryModel>
    private var selectedPosition = 0
    private var isLoading = false
    private lateinit var currentUserModel: UserModel

    override fun onActivityReady() {
        binding.viewModel = viewModel

        /** View Initialization & Setup UI & Data */
        initView()

        /** Recycler View Name Adapter */
        manageProgramNameAdapter()

        /** Recycler View Adapters */
        manageProgramAdapter()

       channelUnderCategoryAdapter()

        setDateAdapter()
        setTimeEventAdapter()
    }

    /** View Initialization & Setup UI & Data */
    private fun initView() {
        currentUserModel = sharedPrefs.getCurrentUser()
        if (currentUserModel.playListType.equals(ConstantUtil.FILE, true)) {
            binding.apply {
                etSearch.gone()
//                dataLinearLayout.gone()
//                exploreAllLinearLayout.visible()
            }
        } else {
         //   setupObserver()
            editTextListeners()
        }
    }

    /** Edit Text Listener */
    private fun editTextListeners() {
        binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                    //fetchAllVideoStream()
                    return true
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()

        /** Update Date & Time According to User Preferences */
//        updateDateTimePreferences()
    }

    /**
     * Update Date & Time According to User Preferences
     */
//    private fun updateDateTimePreferences() {
//        val is24HourFormat = sharedPrefs.getBoolean(ConstantUtil.IS_24_HOURS_FORMAT)
//        if (is24HourFormat) {
//            binding.tvTime.format24Hour = "HH:mm"
//            binding.tvTime.format12Hour = "HH:mm"
//            binding.tvTimeFormat.visibility = View.INVISIBLE
//        } else {
//            binding.tvTime.format24Hour = "hh:mm"
//            binding.tvTime.format12Hour = "hh:mm"
//            binding.tvTimeFormat.visibility = View.VISIBLE
//        }
//    }

    /**
     * Observer Listeners
     */
    private fun setupObserver() {
        if (!isLoading) {
            isLoading = true
            ProgressDialogPref.showLoader(this@LiveProgramListNewActivity)
        }
//        binding.loadingLinearLayout.visible()
        viewModel.liveCategoriesData.observe(this) {
            it?.let {
                fetchAllVideoStream()
                programNameList.addAll(it)
                programNameAdapter.notifyItemRangeInserted(0, programNameList.size)
            }
        }

//        viewModel.isFocusOnList.observe(this) {
//            binding.mainContainerLinearLayout.clearFocus()
//            if (it) {
//                binding.rvProgramListView.requestFocus()
//
//                Handler(Looper.getMainLooper()).postDelayed({
//                    if (programGridPaginationAdapter.itemCount > 0) {
//                        binding.rvProgramListView.requestFocus()
//                        binding.rvProgramListView.smoothScrollToPosition(0)
//                    }
//                }, 500)
//            }
//        }
    }

    /**
     * Fetch All Video Streams
     */
    private fun fetchAllVideoStream() {
//        if (!isLoading) {
//            isLoading = true
//            ProgressDialogPref.showLoader(this@LiveProgramListNewActivity)
//        }
//        binding.loadingLinearLayout.visible()

        viewModel.liveCategoriesData.value?.let {
            val selectedModel = it[selectedPosition]
            fetchSelectedVideoStreamCategories(selectedModel.categoryId)
        }
    }

    /**
     * Fetch All Video Streams as per Category Id
     */
    private fun fetchSelectedVideoStreamCategories(categoryId: String) {
        lifecycleScope.launch {
            viewModel.getSelectedLiveStream(categoryId, binding.etSearch.text.toString())
                .collectLatest {
                    try {
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isLoading) {
                                ProgressDialogPref.hideLoader()
                                isLoading = false
                            }
//                            binding.loadingLinearLayout.gone()
//                            binding.rvProgramGridView.smoothScrollToPosition(0)
//                            binding.rvProgramListView.smoothScrollToPosition(0)
//                            if (programGridPaginationAdapter.itemCount == 0) {
//                                binding.noDataTextView.visible()
//                            } else {
//                                binding.noDataTextView.gone()
//                            }

                        }, 500)
                        programGridPaginationAdapter.submitData(lifecycle, it)
                        programListPaginationAdapter.submitData(lifecycle, it)


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        }
    }

    /**
     * Recycler View Adapters
     */
    private fun manageProgramAdapter() {
//        binding.rvProgramGridView.setHasFixedSize(true)
//        programGridPaginationAdapter = LiveProgramGridViewPagingAdapter(this)
//        binding.rvProgramGridView.adapter = programGridPaginationAdapter
//
//        binding.rvProgramListView.setHasFixedSize(true)
//        programListPaginationAdapter = LiveProgramListViewPagingAdapter(this)
//        binding.rvProgramListView.adapter = programListPaginationAdapter
    }

    /**
     * Recycler View Name Adapter
     */
    private fun manageProgramNameAdapter() {
        programNameList = arrayListOf(
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

        programNameAdapter = LiveProgramNameListNewAdapter(programNameList) { pos ->
            selectedPosition = pos
            binding.selectedChannelsTitleTxt.text=programNameList.get(pos).categoryName
            binding.rvProgramNameList.smoothScrollToPosition(pos)
            //fetchAllVideoStream()
        }
        binding.rvProgramNameList.adapter = programNameAdapter
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


        val mLayoutManager = LinearLayoutManager(this)
        binding.rvChannelName.layoutManager = mLayoutManager

        channelUnderCategoryAdapter = ChannelUnderCategoryAdapter(channelsList) { pos ->
         //   selectedPosition = pos
            binding.imgChannelLogo.setImageResource(com.dhiman.iptv.R.drawable.ic_launcher_background)
            binding.rvChannelName.smoothScrollToPosition(pos)
          //  fetchAllVideoStream()
        }
        binding.rvChannelName.adapter = channelUnderCategoryAdapter
    }


    fun setDateAdapter(){
        val dates = listOf("15 Mar 2024", "16 Mar 2024", "17 Mar 2024")

       dateAdapter = DateAdapter(dates) { selectedDate ->
            // Do something with selected date
            Toast.makeText(this@LiveProgramListNewActivity, "Selected: $selectedDate", Toast.LENGTH_SHORT).show()
        }
        binding.rvDate.adapter = dateAdapter

    }

    fun setTimeEventAdapter(){

        binding.rvTime.layoutManager = LinearLayoutManager(this)

        val scheduleList = listOf(
            "9:30 AM" to "India vs Australia Test Match",
            "9:30 AM" to "India vs Australia Test Match",
            "9:30 AM" to "India vs Australia Test Match"
        )

        scheduleAdapter = ScheduleAdapter(scheduleList)
        binding.rvTime.adapter=scheduleAdapter


    }
    private val activityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val data = it.data
                data?.let { it1 -> refreshAdapter(it1) }
            }
        }

    /**
     * Refresh Paging Adapter
     */
    private fun refreshAdapter(data: Intent) {
        val pos = data.getIntExtra("pos", 0)
        val isFav = data.getBooleanExtra("is_fav", false)

        programGridPaginationAdapter.snapshot().items.toMutableList()[pos].isFav = isFav
        programListPaginationAdapter.snapshot().items.toMutableList()[pos].isFav = isFav
        programGridPaginationAdapter.notifyItemChanged(pos)
        programListPaginationAdapter.notifyItemChanged(pos)
    }

    /**
     * RecyclerView Click Listeners
     */
    override fun onClick(view: View, position: Int, selectedModel: Any, childPosition: Int) {
        if (selectedModel is LiveStreamCategory) {
            if (childPosition == -1) {
                val dataArrayList = ArrayList<String>()
                val channelIdsArrayList = ArrayList<String>()
                val channelsNameArrayList = ArrayList<String>()
                for (x in 0 until programListPaginationAdapter.snapshot().items.size) {
                    val model = programListPaginationAdapter.snapshot().items[x]

                    val movieLink =
                        currentUserModel.mainUrl + "/" + currentUserModel.userName + "/" + currentUserModel.password + "/" + model.streamId
                    dataArrayList.add(movieLink)
                    channelIdsArrayList.add(model.streamId)
                    channelsNameArrayList.add(model.name)
                }

                val liveFullUrl =
                    currentUserModel.mainUrl + "/" + currentUserModel.userName + "/" + currentUserModel.password + "/" + selectedModel.streamId

                /** MultiScreen Resource Selection */
                if (intent.hasExtra(ConstantUtil.INTENT_MULTI_SCREEN_RESOURCE)) {
                    val returnIntent = Intent()
                    returnIntent.putExtra(ConstantUtil.INTENT_VIDEO_URL, liveFullUrl)
                    setResult(RESULT_OK, returnIntent)
                    finish()
                }
                /** PLay Video on Selection */
                else {
                    val intent = Intent(this, PlayerActivity::class.java)
                    intent.putExtra(ConstantUtil.INTENT_ID, liveFullUrl)
                    intent.putExtra(ConstantUtil.INTENT_TYPE, ConstantUtil.INTENT_LIVE)
                    intent.putExtra(ConstantUtil.INTENT_SELECTED_POS, position)
                    intent.putExtra(ConstantUtil.INTENT_SERIES_EPISODES_LIST, dataArrayList)
                    intent.putExtra(ConstantUtil.INTENT_CHANNEL_ID_LIST, channelIdsArrayList)
                    intent.putExtra(ConstantUtil.INTENT_CHANNEL_NAME_LIST, channelsNameArrayList)
                    startActivity(intent)
                }

                val intent = Intent(this, LiveProgramListActivity::class.java)
                intent.putExtra(ConstantUtil.INTENT_ID, selectedModel.streamId)
                intent.putExtra(ConstantUtil.DATA_MODEL, Gson().toJson(selectedModel))
                intent.putExtra(ConstantUtil.LIVE_PROGRAM_NAME, selectedModel.name)
                intent.putExtra(ConstantUtil.LIVE_PROGRAM_IMAGE, selectedModel.streamIcon)
                intent.putExtra(ConstantUtil.POSITION, position)
                activityResult.launch(intent)
            } else {
                val isFavModel = selectedModel.isFav
                selectedModel.isFav = !isFavModel

                (programGridPaginationAdapter.snapshot().items as MutableList<LiveStreamCategory>)[position].isFav =
                    !isFavModel
                programGridPaginationAdapter.notifyItemChanged(position)

                (programListPaginationAdapter.snapshot().items as MutableList<LiveStreamCategory>)[position].isFav =
                    !isFavModel
                programListPaginationAdapter.notifyItemChanged(position)

                viewModel.updateLiveStreamCategory(selectedModel)

                if (selectedPosition == 0) {
                    programGridPaginationAdapter.refresh()
                    programListPaginationAdapter.refresh()
                }
            }
        }

    }

}