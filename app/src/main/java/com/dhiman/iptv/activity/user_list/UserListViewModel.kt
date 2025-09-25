package com.dhiman.iptv.activity.user_list

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.dialog.add_play_list.AddPlayListDialog
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val roomRepository: RoomRepository) :
    BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val allUserList = MutableLiveData<List<UserDataModel>>()

    init {
        fetchAllUser()
    }

    /**
     * Fetch All Users from Room Database
     */
    private fun fetchAllUser() {
        viewModelScope.launch {
            val allUsers = roomRepository.getAllUsers()
            allUserList.postValue(allUsers)
        }
    }

    /**
     * Delete User from Room Database
     */
    fun deleteUser(model: UserDataModel) {
        viewModelScope.launch {
            roomRepository.deleteUser(model)
            delay(500)
            fetchAllUser()
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

//            R.id.btnAddUser -> {
//                view.context.apply {
//                    val addPlayListDialog = AddPlayListDialog()
//                    addPlayListDialog.show((this as AppCompatActivity).supportFragmentManager, "")
//                    addPlayListDialog.callBack = {
//                        fetchAllUser()
//                    }
//                }
//            }
        }
    }

}