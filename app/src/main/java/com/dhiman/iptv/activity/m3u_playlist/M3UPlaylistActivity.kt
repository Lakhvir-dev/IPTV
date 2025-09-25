package com.dhiman.iptv.activity.m3u_playlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.M3UItemModel
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.databinding.ActivityM3UplaylistBinding
import com.dhiman.iptv.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class M3UPlaylistActivity : AppCompatActivity(), RecyclerViewClickListener {

    @Inject
    lateinit var sharedPrefs: SharedPrefs
    private val viewModel: M3UPlaylistViewModel by viewModels()
    lateinit var binding: ActivityM3UplaylistBinding
    private var selectedPosition = 0
    private var isLoading = false
    private lateinit var programNameList: ArrayList<Any>
    private lateinit var programNameAdapter: M3UPlaylistNameListAdapter
    private lateinit var programListPaginationAdapter: M3UPlaylistListViewPagingAdapter
    private lateinit var programGridPaginationAdapter: M3UPlaylistGridViewPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_m3_uplaylist)
        binding.viewModel = viewModel

        /** Name Adapter Setup */
        manageProgramNameAdapter()

        /** Grid, List Adapter Setup */
        manageProgramAdapter()

        /** Observer Listeners */
        setupObserver()

        /** Edit Text Listeners */
        editTextListeners()

        /** Set up onFocus Change Listeners */
        setUpFocusChangeListeners()
    }

    /**
     * Set up onFocus Change Listeners
     */
    private fun setUpFocusChangeListeners() {
//        binding.rvProgramNameList.setOnFocusChangeListener { _, hasFocus ->
//            if (hasFocus) {
//                Handler(Looper.getMainLooper()).postDelayed({
//                    binding.ivTopArrow.requestFocus()
//                }, 100)
//            }
//        }

        binding.ivBack.requestFocus()
        Handler(Looper.getMainLooper()).postDelayed({
            binding.ivBack.requestFocus()
        }, 500)
    }

    /**
     * Edit Text Listeners
     */
    private fun editTextListeners() {
        binding.etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard()
                    fetchAllVideoStream()
                    return true
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()

        /** Update Date & Time According to User Preferences */
        updateDateTimePreferences()
    }

    /**
     * Update Date & Time According to User Preferences
     */
    private fun updateDateTimePreferences() {
        val is24HourFormat = sharedPrefs.getBoolean(ConstantUtil.IS_24_HOURS_FORMAT)
        if (is24HourFormat) {
            binding.tvTime.format24Hour = "HH:mm"
            binding.tvTime.format12Hour = "HH:mm"
            binding.tvTimeFormat.visibility = View.INVISIBLE
        } else {
            binding.tvTime.format24Hour = "hh:mm"
            binding.tvTime.format12Hour = "hh:mm"
            binding.tvTimeFormat.visibility = View.VISIBLE
        }
    }

    /**
     * Observer Listeners
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {
        if (!isLoading) {
            isLoading = true
            ProgressDialogPref.showLoader(this@M3UPlaylistActivity)
        }
        binding.loadingLinearLayout.visible()
        viewModel.commonCategoriesData.observe(this) {
            it?.let {
                fetchAllVideoStream()
                programNameList.addAll(it)
                programNameAdapter.notifyItemRangeInserted(0, programNameList.size)
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.rvProgramNameList.smoothScrollToPosition(0)
                }, 500)
            }
        }

        viewModel.isFocusOnList.observe(this) {
            binding.mainContainerLinearLayout.clearFocus()
            if (it) {
                binding.rvProgramListView.requestFocus()

                Handler(Looper.getMainLooper()).postDelayed({
                    if (programGridPaginationAdapter.itemCount > 0) {
                        binding.rvProgramListView.requestFocus()
                        binding.rvProgramListView.smoothScrollToPosition(0)
                    }
                }, 500)
            }
        }
    }

    /**
     * Fetch Video with Selected Category Id
     */
    private fun fetchAllVideoStream() {
//        if (!isLoading) {
//            isLoading = true
//            ProgressDialogPref.showLoader(this@M3UPlaylistActivity)
//        }
        binding.loadingLinearLayout.visible()

        viewModel.commonCategoriesData.value?.let {
            val selectedModel = it[selectedPosition]
            fetchSelectedVideoStreamCategories(selectedModel.category_id)
        }
    }

    /**
     * Fetch Video with Selected Category Id From Room Database
     */
    private fun fetchSelectedVideoStreamCategories(categoryId: String) {
        lifecycleScope.launch {
            viewModel.getSelectedM3UPlaylist(categoryId, binding.etSearch.text.toString())
                .collectLatest {
                    Log.e("it", ""+it)
                    try {
                        Handler(Looper.getMainLooper()).postDelayed({
                            if (isLoading) {
                                ProgressDialogPref.hideLoader()
                                isLoading = false
                            }
                            binding.loadingLinearLayout.gone()
                            binding.rvProgramGridView.smoothScrollToPosition(0)
                            binding.rvProgramListView.smoothScrollToPosition(0)
                            if (programGridPaginationAdapter.itemCount == 0) {
                                binding.noDataTextView.visible()
                            } else {
                                binding.noDataTextView.gone()
                            }
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
     * Grid, List Adapter Setup
     */
    private fun manageProgramAdapter() {
        binding.rvProgramGridView.setHasFixedSize(true)
        programGridPaginationAdapter = M3UPlaylistGridViewPagingAdapter(this)
        binding.rvProgramGridView.adapter = programGridPaginationAdapter

        binding.rvProgramListView.setHasFixedSize(true)
        programListPaginationAdapter = M3UPlaylistListViewPagingAdapter(this)
        binding.rvProgramListView.adapter = programListPaginationAdapter
    }

    /**
     * Name Adapter Setup
     */
    private fun manageProgramNameAdapter() {
        programNameList = ArrayList()
        val mLayoutManager = LinearLayoutManager(this)
        binding.rvProgramNameList.layoutManager = mLayoutManager

        programNameAdapter = M3UPlaylistNameListAdapter(programNameList) { pos ->
            selectedPosition = pos
            binding.rvProgramNameList.smoothScrollToPosition(pos)
            fetchAllVideoStream()
        }
        binding.rvProgramNameList.adapter = programNameAdapter

        binding.rvProgramNameList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val centerView: View? = LinearSnapHelper().findSnapView(mLayoutManager)
                    val pos = centerView?.let { mLayoutManager.getPosition(it) }
                    if (selectedPosition != pos) {
                        if (pos != null) {
                            selectedPosition = pos
                        }
                        fetchAllVideoStream()
                    }
                }
            }
        })

        binding.rvProgramNameList.getViewWidthHeight { width, height ->
            binding.view.getViewWidthHeight { viewWidth, viewHeight ->
                Log.v("vinay", "width $viewWidth height $viewHeight")
                if (mLayoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
                    val padding: Int = (width / 2).minus(dpToPixel(viewWidth.div(4).toFloat()))
                    binding.rvProgramNameList.setPadding(padding, 0, padding, 0)
                } else {
                    val padding: Int = (height / 2).minus(dpToPixel(viewHeight.div(4).toFloat()))
                    binding.rvProgramNameList.setPadding(0, padding, 0, padding)
                }
            }
        }

        // Smart snapping
        LinearSnapHelper().attachToRecyclerView(binding.rvProgramNameList)

        binding.ivTopArrow.setOnClickListener {
            try {
                binding.rvProgramNameList.smoothScrollToPosition(selectedPosition.plus(1))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.ivBottomArrow.setOnClickListener {
            try {
                binding.rvProgramNameList.smoothScrollToPosition(selectedPosition.minus(1))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * RecyclerView Click Listeners
     */
    override fun onClick(view: View, position: Int, selectedModel: Any, childPosition: Int) {
        if (selectedModel is M3UItemModel) {
            val isFavModel = selectedModel.isFavorite
            selectedModel.isFavorite = !isFavModel

            (programGridPaginationAdapter.snapshot().items as MutableList<M3UItemModel>)[position].isFavorite =
                !isFavModel
            programGridPaginationAdapter.notifyItemChanged(position)

            (programListPaginationAdapter.snapshot().items as MutableList<M3UItemModel>)[position].isFavorite =
                !isFavModel
            programListPaginationAdapter.notifyItemChanged(position)

            viewModel.updateVideoStreamCategory(selectedModel)

            if (selectedPosition == 0) {
                programGridPaginationAdapter.refresh()
                programListPaginationAdapter.refresh()
            }
        }
    }

}