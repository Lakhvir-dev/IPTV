package com.dhiman.iptv.activity.catch_up

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
class CatchUpViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : BaseViewModel() {
    val isFocusOnBack = ObservableField(true)
    val catchUpData = MutableLiveData<List<LiveCategoryModel>>()

    fun onFocusChange(view: View, value:Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }
        }
    }

    init {
        fetchAllLiveCategories()
    }

    private fun fetchAllLiveCategories() {
        viewModelScope.launch {
            val allVideoCategoriesList = roomRepository.getAllLiveCategories()
            catchUpData.postValue(allVideoCategoriesList)
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }
//            R.id.btnAddUser -> {
//                view.context.apply {
//
//                }
//            }

        }
    }
}