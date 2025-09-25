package com.dhiman.iptv.activity.provider_status

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.activity.login_type.LoginTypeActivity
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.dialog.logout.LogoutConfirmationDialog
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProviderStatusViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val sharedPrefs: SharedPrefs
) : BaseViewModel() {
    val userAuth = MutableLiveData<Resource<UserAuth>>()
    val isFocusOnBack = ObservableField(true)

    init {
        val currentUser = sharedPrefs.getCurrentUser()
        if (currentUser.playListType.equals(ConstantUtil.XTREAM_URL, true)) {
            getUserDetails(currentUser.mainUrl, currentUser.userName, currentUser.password)
        }
    }

    /**
     * Get User Info Api Call
     */
    private fun getUserDetails(baseUrl: String, username: String, password: String) {
        viewModelScope.launch {
            try {
                userAuth.postValue(Resource.loading(null))
                if (networkHelper.isNetworkConnected()) {
                    mainRepository.userAuth(baseUrl, username, password).let {
                        if (it.isSuccessful && it.body()?.userInfo?.auth == 1 && it.body()!!.userInfo?.status.equals(
                                "Active",
                                true
                            )) {
                            userAuth.postValue(Resource.success(it.body()))
                        } else {
                            userAuth.postValue(Resource.error("URL is not valid or Unauthorised User", null))
                        }
                    }
                } else {
                    userAuth.postValue(Resource.error("No internet connection", null))
                }
            } catch (e: Exception) {
                userAuth.postValue(Resource.error(e.message.toString(), null))
            }
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

            R.id.btnLogout -> {
                view.context.apply {
                    val logoutConfirmationDialog = LogoutConfirmationDialog()
                    logoutConfirmationDialog.show(
                        (this as AppCompatActivity).supportFragmentManager,
                        ""
                    )
                    logoutConfirmationDialog.callBack = {
                        sharedPrefs.clearPreference()
                        startActivity(Intent(this, LoginTypeActivity::class.java))
                        (view.context as AppCompatActivity).finishAffinity()
                    }
                }
            }
        }
    }

}