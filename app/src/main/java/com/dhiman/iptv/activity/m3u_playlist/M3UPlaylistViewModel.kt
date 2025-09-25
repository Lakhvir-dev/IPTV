package com.dhiman.iptv.activity.m3u_playlist

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.CommonCategoryModel
import com.dhiman.iptv.data.local.db.entity.M3UItemModel
import com.dhiman.iptv.data.local.db.pagingSource.M3UPlaylistPagingSource
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class M3UPlaylistViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isMenuSelected = ObservableField(true)
    val commonCategoriesData = MutableLiveData<List<CommonCategoryModel>>()
    val isFocusOnUpArrow = ObservableField(false)
    val isFocusOnDownArrow = ObservableField(false)
    val isFocusOnGridMenu = ObservableField(false)
    val isFocusOnListMenu = ObservableField(false)
    val isFocusOnList = MutableLiveData<Boolean>()

    init {
        fetchAllVideoCategories()
    }

    private fun fetchAllVideoCategories() {
        val model = CommonCategoryModel(
            category_id = ConstantUtil.FAV_CATEGORY_ID,
            category_name = ConstantUtil.FAV_LABEL
        )
        val nonCategory = CommonCategoryModel(
            category_id = "null",
            category_name = ConstantUtil.NO_CATEGORIES_LABEL
        )
        viewModelScope.launch {
            val allCommonCategoriesList = roomRepository.getAllCommonCategories().toMutableList()
            allCommonCategoriesList.add(0, model)
            allCommonCategoriesList.add(nonCategory)
            commonCategoriesData.postValue(allCommonCategoriesList)
        }
    }

    fun getSelectedM3UPlaylist(categoryId: String, searchQuery: String) = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20,
        )
    ) {
        M3UPlaylistPagingSource(roomRepository, categoryId, searchQuery)
    }.flow.cachedIn(viewModelScope)

    fun updateVideoStreamCategory(model: M3UItemModel) {
        viewModelScope.launch {
            roomRepository.updateM3UPlaylist(model)
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
                isFocusOnListMenu.set(false)
                isFocusOnList.postValue(true)
            }
        }
    }

}