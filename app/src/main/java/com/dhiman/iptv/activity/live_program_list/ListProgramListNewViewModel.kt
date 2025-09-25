package com.dhiman.iptv.activity.live_program_list

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.data.local.db.pagingSource.LiveProgramPagingSource
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProgramListNewViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val liveCategoriesData = MutableLiveData<List<LiveCategoryModel>>()

    init {
        fetchAllLiveCategories()
    }

    private fun fetchAllLiveCategories() {
        val model = LiveCategoryModel(
            categoryId = ConstantUtil.FAV_CATEGORY_ID,
            categoryName = ConstantUtil.FAV_LABEL
        )
        viewModelScope.launch {
            val allVideoCategoriesList = roomRepository.getAllLiveCategories().toMutableList()
            allVideoCategoriesList.add(0, model)
            liveCategoriesData.postValue(allVideoCategoriesList)
        }
    }

    fun getSelectedLiveStream(categoryId: String, searchQuery: String) = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20,
        )
    ) {
        LiveProgramPagingSource(roomRepository, categoryId, searchQuery)
    }.flow.cachedIn(viewModelScope)

    fun updateLiveStreamCategory(model: LiveStreamCategory) {
        viewModelScope.launch {
            roomRepository.updateLiveStreamCategory(model)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }
        }
    }
}