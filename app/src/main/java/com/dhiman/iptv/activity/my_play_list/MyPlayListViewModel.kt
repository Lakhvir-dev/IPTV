package com.dhiman.iptv.activity.my_play_list

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.UserDataModel
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.dialog.delete_play_list.DeletePlayListDialog
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPlayListViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isFocusOnAddButton = ObservableField(false)
    val isFocusOnUpdateButton = ObservableField(false)
    val isFocusOnRefreshButton = ObservableField(false)
    val isFocusOnDeleteButton = ObservableField(false)
    val isAddNewPlayList = ObservableField(false)
    val allUserList = MutableLiveData<List<UserDataModel>>()
    val userAuth = MutableLiveData<Resource<UserAuth>>()
    val selectedAdapterPosition = MutableLiveData<Int>()
    val selectedAdapterUser = MutableLiveData<UserDataModel>()
    val isUpdatePlayList = MutableLiveData<Boolean>()
    val playUserList = MutableLiveData<Boolean>()

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.btnAddPlayList -> {
                isFocusOnAddButton.set(value)
            }
            R.id.btnUpdatePlayList -> {
                isFocusOnUpdateButton.set(value)
            }

            R.id.btnRefreshPlayList -> {
                isFocusOnRefreshButton.set(value)
            }
            R.id.btnDeletePlayList -> {
                isFocusOnDeleteButton.set(value)
            }

            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }
        }

    }

    init {
        fetchAllUser()
        playUserList.postValue(false)
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
     * Add User to Room Database
     */
    fun addUserToRoomDatabase(model: UserDataModel) {
        viewModelScope.launch {
            roomRepository.insertUser(model)
            fetchAllUser()
            isAddNewPlayList.set(false)
        }
    }

    /**
     * Update User in Room Database
     */
    fun updateUserRoomDatabase(model: UserDataModel) {
        viewModelScope.launch {
            roomRepository.updateUser(model)
            fetchAllUser()
            isUpdatePlayList.postValue(false)
            isAddNewPlayList.set(false)
        }
    }

    /**
     * User Authentication Api Call
     */
    fun userAuth(baseUrl: String, username: String, password: String) {
        viewModelScope.launch {
            userAuth.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.userAuth(baseUrl, username, password).let {
                    if (it.isSuccessful && it.body()?.userInfo?.auth == 1 && it.body()!!.userInfo?.status.equals(
                            "Active",
                            true
                        )
                    ) {
                        userAuth.postValue(Resource.success(it.body()))
                    } else {
                        userAuth.postValue(Resource.error("URL is not valid or Unauthorised User", null))
                    }
                }
            } else {
                userAuth.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnAddPlayList -> {
                manageFocus(isFocusOnAddButton)
                isAddNewPlayList.set(true)
                isUpdatePlayList.postValue(false)
            }

            R.id.btnUpdatePlayList -> {
                manageFocus(isFocusOnUpdateButton)
                if (isAddNewPlayList.get() == true) {
                    isAddNewPlayList.set(false)
                } else if (!checkListEmpty()) {
                    isUpdatePlayList.postValue(true)
                    isAddNewPlayList.set(true)
                } else {
                    isAddNewPlayList.set(false)
                }
            }

            R.id.btnRefreshPlayList -> {
                manageFocus(isFocusOnRefreshButton)
                if (isAddNewPlayList.get() == true) {
                    isAddNewPlayList.set(false)
                } else if (!checkListEmpty()) {
                    playUserList.postValue(true)
//                    view.context.apply {
//                        val refreshPlayListDialog = RefreshPlayListDialog()
//                        refreshPlayListDialog.show(
//                            (this as AppCompatActivity).supportFragmentManager,
//                            ""
//                        )
//                        refreshPlayListDialog.callBack = {
//                            "Data Refresh Successfully".shortToast(view.context)
//                            // startActivity(Intent(this, UserListActivity::class.java))
//                        }
//                    }
                }
            }

            R.id.btnDeletePlayList -> {
                manageFocus(isFocusOnDeleteButton)
                if (isAddNewPlayList.get() == true) {
                    isAddNewPlayList.set(false)
                } else if (!checkListEmpty()) {
                    view.context.apply {
                        val deletePlayListDialog = DeletePlayListDialog()
                        deletePlayListDialog.show(
                            (this as AppCompatActivity).supportFragmentManager,
                            ""
                        )
                        deletePlayListDialog.callBack = {
                            selectedAdapterUser.value?.let {
                                viewModelScope.launch {
                                    roomRepository.deleteUser(it)
                                    fetchAllUser()
                                }
                            }
                        }
                    }
                }
            }

            R.id.ivBack -> {
                (view.context as AppCompatActivity).finish()
            }
        }
    }

    /**
     * Focus Management of Views
     */
    private fun manageFocus(view: ObservableField<Boolean>) {
        isFocusOnAddButton.set(false)
        isFocusOnUpdateButton.set(false)
        isFocusOnRefreshButton.set(false)
        isFocusOnDeleteButton.set(false)
        view.set(true)
    }

    private fun checkListEmpty(): Boolean {
        var isEmpty = false
        allUserList.value?.let {
            val dataList = ArrayList<UserDataModel>()
            for (element in it) {
                if (element.userModel.playListType.equals(ConstantUtil.XTREAM_URL, true)) {
                    dataList.add(element)
                }
            }
            isEmpty = dataList.isEmpty()
        }
        return isEmpty
    }
}