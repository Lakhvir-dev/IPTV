package com.dhiman.iptv.activity.player

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.model.catchUp.CatchUpModel
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.dialog.movie_display.MovieDisplayDialog
import com.dhiman.iptv.dialog.movie_subtitile.MovieSubtitleDialog
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val apiRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    sharedPrefs: SharedPrefs
) : BaseViewModel() {

    val isVideoPlay = ObservableField(true)
    val currentUserModel: UserModel = sharedPrefs.getCurrentUser()
    val channelEPGLiveData = MutableLiveData<Resource<CatchUpModel>>()

    //    val isAddNewPlayList = ObservableField(false)
//    val headerText = ObservableField<String>()
//    val isFocusOnAddButton = ObservableField(false)
//    val isFocusOnUpdateButton = ObservableField(false)
//    val isFocusOnRefreshButton = ObservableField(false)
//    val isFocusOnDeleteButton = ObservableField(false)
//    val isFocusOnRewind = ObservableField(false)
//    val isFocusOnForward = ObservableField(false)
//    val isFocusOnSubtitle = ObservableField(false)
//    val isFocusOnFillScreen = ObservableField(false)
//    val isFocusOnFitScreen = ObservableField(false)
    val isFocusOnLockScreen = ObservableField(false)

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.btnUnlock -> {
                isFocusOnLockScreen.set(value)
            }
//
//            R.id.exo_ffwd -> {
//                isFocusOnForward.set(value)
//            }
//
//            R.id.btnSubtitle -> {
//                isFocusOnSubtitle.set(value)
//            }
//
//            R.id.btnDisplay -> {
//                isFocusOnFillScreen.set(value)
//            }
//
//            R.id.btnDisplayMode -> {
//                isFocusOnFitScreen.set(value)
//            }
//
//            R.id.exoPlayerLockButton -> {
//                isFocusOnLockScreen.set(value)
//            }
//
////               R.id.btnAddPlayList -> {
////                   isFocusOnAddButton.set(value)
////               }
////               R.id.btnUpdatePlayList -> {
////                   isFocusOnUpdateButton.set(value)
////               }
////
////               R.id.btnRefreshPlayList -> {
////                   isFocusOnRefreshButton.set(value)
////               }
////               R.id.btnDeletePlayList -> {
////                   isFocusOnDeleteButton.set(value)
////               }
////
////               R.id.ivBack -> {
//////                   isFocusOnBack.set(value)
////               }
        }

    }


    fun onClick(view: View) {
        when (view.id) {
//            R.id.ivPlayPause -> {
//                isVideoPlay.set(isVideoPlay.get()?.not())
//            }

            R.id.btnSubtitle -> {
                view.context.apply {
                    val movieSubtitleDialog = MovieSubtitleDialog()
                    movieSubtitleDialog.show((this as AppCompatActivity).supportFragmentManager, "")
                    movieSubtitleDialog.callBack = {
                        // startActivity(Intent(this, UserListActivity::class.java))
                    }
                }
            }
            R.id.btnDisplay -> {
                view.context.apply {
                    val movieDisplayDialog = MovieDisplayDialog()
                    movieDisplayDialog.show((this as AppCompatActivity).supportFragmentManager, "")
                    movieDisplayDialog.callBack = {
                        // startActivity(Intent(this, UserListActivity::class.java))
                    }
                }
            }
        }
    }

    /**
     * Get Channel's EPG Programs Api Call
     */
    fun getChannelEPGApi(streamId: String) {
        viewModelScope.launch {
            channelEPGLiveData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    apiRepository.getChannelEPGPrograms(
                        currentUserModel,
                        ConstantUtil.SHORT_EPG_ACTION,
                        streamId
                    )
                        .let {
                            if (it.isSuccessful) {
                                channelEPGLiveData.postValue(Resource.success(it.body()))
                            } else {
                                channelEPGLiveData.postValue(
                                    Resource.error(
                                        "URL is not valid",
                                        null
                                    )
                                )
                            }
                        }
                } catch (e: Exception) {
                    channelEPGLiveData.postValue(Resource.error(e.message.toString(), null))
                }
            } else {
                channelEPGLiveData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

}