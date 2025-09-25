package com.dhiman.iptv.activity.movie_program_list

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.VideoCategoryModel
import com.dhiman.iptv.data.local.db.entity.VideoStreamCategory
import com.dhiman.iptv.data.local.db.pagingSource.MovieProgramPagingSource
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieProgramListViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isMenuSelected = ObservableField(true)
    val videoCategoriesData = MutableLiveData<List<VideoCategoryModel>>()

    //    val selectedVideosLiveData = MutableLiveData<List<VideoStreamCategory>>()
    val isFocusOnUpArrow = ObservableField(false)
    val isFocusOnDownArrow = ObservableField(false)
    val isFocusOnGridMenu = ObservableField(false)
    val isFocusOnListMenu = ObservableField(false)
    val isFocusOnList = MutableLiveData<Boolean>()

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }

            R.id.ivTopArrow -> {
                isFocusOnUpArrow.set(value)
            }

            R.id.ivBottomArrow -> {
                isFocusOnDownArrow.set(value)
            }

            R.id.ivMenu -> {
                isFocusOnGridMenu.set(value)
            }

            R.id.ivList -> {
                isFocusOnListMenu.set(value)
            }
        }
    }

    init {
        fetchAllVideoCategories()
    }

    /**
     * Fetch All Video Categories
     */
    private fun fetchAllVideoCategories() {
        val model = VideoCategoryModel(
            categoryId = ConstantUtil.FAV_CATEGORY_ID,
            categoryName = ConstantUtil.FAV_LABEL
        )
        viewModelScope.launch {
            val allVideoCategoriesList = roomRepository.getAllVideoCategories().toMutableList()
            allVideoCategoriesList.add(0, model)
            videoCategoriesData.postValue(allVideoCategoriesList)
        }
    }

    /**
     * Update Video Stream Category
     */
    fun updateVideoStreamCategory(model: VideoStreamCategory) {
        viewModelScope.launch {
            roomRepository.updateVideoStreamCategory(model)
        }
    }

    /**
     * Get Selected Video Stream
     */
    fun getSelectedVideoStream(categoryId: String, searchQuery: String) = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20,
        )
    ) {
        MovieProgramPagingSource(roomRepository, categoryId, searchQuery)
    }.flow.cachedIn(viewModelScope)

//    fun getSelectedVideos(categoryId: String, searchQuery: String) {
//        viewModelScope.launch {
//            val dataList = roomRepository.getSelectedVideoStreamCategories(categoryId, searchQuery)
//            selectedVideosLiveData.postValue(dataList)
//        }
//    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }

            R.id.ivMenu -> {
                isMenuSelected.set(true)
            }

            R.id.ivList -> {
                isMenuSelected.set(false)
                isFocusOnListMenu.set(false)
                isFocusOnList.postValue(true)
            }
        }
    }

}