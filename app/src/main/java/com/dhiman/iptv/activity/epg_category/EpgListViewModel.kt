package com.dhiman.iptv.activity.epg_category

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpgListViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {
    val epgCategoriesData = MutableLiveData<List<LiveCategoryModel>>()
    val isFocusOnBack = ObservableField(true)

    init {
        fetchAllLiveCategories()
    }

    private fun fetchAllLiveCategories() {
        viewModelScope.launch {
            val allVideoCategoriesList = roomRepository.getAllLiveCategories()
            epgCategoriesData.postValue(allVideoCategoriesList)
        }
    }

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }
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