package com.dhiman.iptv.activity.series_program

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.SeriesCategoryModel
import com.dhiman.iptv.data.local.db.entity.SeriesStreamCategory
import com.dhiman.iptv.data.local.db.pagingSource.SeriesProgramPagingSource
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesProgramListViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isMenuSelected = ObservableField(true)
    val seriesCategoriesData = MutableLiveData<List<SeriesCategoryModel>>()
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
        fetchAllSeriesCategories()
    }

    /**
     * Get All Categories of Series from Room Database
     */
    private fun fetchAllSeriesCategories() {
        val model = SeriesCategoryModel(
            categoryId = ConstantUtil.FAV_CATEGORY_ID,
            categoryName = ConstantUtil.FAV_LABEL
        )
        viewModelScope.launch {
            val allVideoCategoriesList = roomRepository.getAllSeriesCategories().toMutableList()
            allVideoCategoriesList.add(0, model)
            seriesCategoriesData.postValue(allVideoCategoriesList)
        }
    }

    /**
     * Update Series in Room Database
     */
    fun updateSeriesStreamCategory(model: SeriesStreamCategory) {
        viewModelScope.launch {
            roomRepository.updateSeriesStreamCategory(model)
        }
    }

    /**
     * Get Selected Series from Room Database
     */
    fun getSelectedSeriesStream(
        categoryId: String,
        searchQuery: String
    ) = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20,
        )
    ) {
        SeriesProgramPagingSource(roomRepository, categoryId, searchQuery)
    }.flow.cachedIn(viewModelScope)

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