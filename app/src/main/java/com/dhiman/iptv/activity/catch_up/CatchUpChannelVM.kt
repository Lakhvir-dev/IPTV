package com.dhiman.iptv.activity.catch_up

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.model.catchUp.CatchUpModel
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatchUpChannelVM @Inject constructor(
    private val apiRepository: MainRepository,
    private val networkHelper: NetworkHelper,
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val catchUpChannelProgramsLiveData = MutableLiveData<Resource<CatchUpModel>>()

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

    fun getAllChannelProgramsApi(currentUserModel: UserModel, streamId: String) {
        viewModelScope.launch {
            catchUpChannelProgramsLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    apiRepository.getCatchUpChannelPrograms(
                        currentUserModel,
                        ConstantUtil.CATCH_UP_ACTION,
                        streamId
                    )
                        .let {
                            if (it.isSuccessful) {
                                 catchUpChannelProgramsLiveData.postValue(Resource.success(it.body()))
                            } else {
                                catchUpChannelProgramsLiveData.postValue(
                                    Resource.error(
                                        "URL is not valid",
                                        null
                                    )
                                )
                            }
                        }
                } catch (e: Exception) {
                     catchUpChannelProgramsLiveData.postValue(Resource.error(e.message.toString(), null))
                }
            } else {
                catchUpChannelProgramsLiveData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

}