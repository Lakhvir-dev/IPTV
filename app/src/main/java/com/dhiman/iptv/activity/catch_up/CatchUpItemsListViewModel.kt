package com.dhiman.iptv.activity.catch_up

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.data.local.db.pagingSource.CatchUpProgramPagingSource
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatchUpItemsListViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isMenuSelected = ObservableField(true)
    val isFocusOnUpArrow = ObservableField(false)
    val isFocusOnDownArrow = ObservableField(false)
    val isFocusOnGridMenu = ObservableField(false)
    val isFocusOnListMenu = ObservableField(false)
    val catchUpData = MutableLiveData<List<LiveCategoryModel>>()

    init {
        fetchAllLiveCategories()
    }

    private fun fetchAllLiveCategories() {
//        val model = LiveCategoryModel(
//            categoryId = ConstantUtil.FAV_CATEGORY_ID,
//            categoryName = ConstantUtil.FAV_LABEL
//        )
        viewModelScope.launch {
            val allVideoCategoriesList = roomRepository.getAllLiveCategories()
          //  allVideoCategoriesList.add(0, model)
            catchUpData.postValue(allVideoCategoriesList)
        }
    }

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
            }
        }
    }

    /** Get All Catch Up Stream Data */
    fun getSelectedCatchUpStream(categoryId: String, searchQuery: String) = Pager(
        PagingConfig(pageSize = 15, enablePlaceholders = false)
    ) {
        CatchUpProgramPagingSource(roomRepository, categoryId, searchQuery)
    }.flow.cachedIn(viewModelScope)

    /** Update Catch Up Stream Data */
    fun updateCatchUpStreamCategory(model: LiveStreamCategory) {
        viewModelScope.launch {
            roomRepository.updateLiveStreamCategory(model)
        }
    }

}