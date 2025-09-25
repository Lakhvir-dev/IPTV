package com.dhiman.iptv.dialog.xtream_code

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class XtreamCodeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    val _userAuth = MutableLiveData<Resource<UserAuth>>()

    val isFocusOnBack = ObservableField(true)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivCross -> {
                isFocusOnBack.set(value)
            }
        }
    }

    fun userAuth(baseUrl: String, username: String, password: String) {
        viewModelScope.launch {
            _userAuth.postValue(Resource.loading(null))

            try {
                val response = networkHelper.retrofitErrorHandler(
                    mainRepository.userAuth(
                        baseUrl,
                        username,
                        password
                    )
                )
                if (response.userInfo?.auth == 1 && response.userInfo.status.equals(
                        "Active",
                        true
                    )
                ) {
                    _userAuth.postValue(Resource.success(response))
                } else {
                    _userAuth.postValue(
                        Resource.error(
                            "User Name and password is not valid or Unauthorised User",
                            null
                        )
                    )
                }
            } catch (e: Exception) {
                _userAuth.postValue(Resource.error(e.message.toString(), null))
            }


//            if (networkHelper.isNetworkConnected()) {
//                mainRepository.userAuth(baseUrl, username, password).let {
//                    if (it.isSuccessful && it.body()?.userInfo?.auth == 1) {
//                        _userAuth.postValue(Resource.success(it.body()))
//                    } else {
//                        _userAuth.postValue(Resource.error("URL is not valid", null))
//                    }
//                }
//            } else {
//                _userAuth.postValue(Resource.error("No internet connection", null))
//            }
        }
    }

}