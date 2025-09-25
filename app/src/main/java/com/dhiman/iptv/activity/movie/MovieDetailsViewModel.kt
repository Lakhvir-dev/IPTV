package com.dhiman.iptv.activity.movie

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.VideoStreamCategory
import com.dhiman.iptv.data.model.MovieInfoModel
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val apiRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isFocusOnWatchTrailer = ObservableField(false)
    val movieInfoData = MutableLiveData<Resource<MovieInfoModel>>()
    val movieYoutubeLink = MutableLiveData<String>()
    val movieFullLink = MutableLiveData<String>()
    val dataModel = MutableLiveData<VideoStreamCategory>()
    val isFavMovie = MutableLiveData<Boolean>()

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }

//            R.id.llWatchTrailer -> {
//                isFocusOnWatchTrailer.set(value)
//            }
        }
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.ivBack -> {
                (view.context as AppCompatActivity).onBackPressed()
            }
//            R.id.llWatchTrailer -> {
//                view.context.apply {
//                    if (movieYoutubeLink.value?.isNotEmpty()!!) {
//                        val intent = Intent(this, YoutubePlayerActivity::class.java)
//                        intent.putExtra(ConstantUtil.INTENT_ID, movieYoutubeLink.value.toString())
//                        startActivity(intent)
//                    }
//                }
//            }
//            R.id.btnWatchMovie -> {
//                view.context.apply {
//                    if (movieFullLink.value?.isNotEmpty()!!) {
//                        val intent = Intent(this, PlayerActivity::class.java)
//                        intent.putExtra(ConstantUtil.INTENT_ID, movieFullLink.value.toString())
//                        startActivity(intent)
//                    }
//                }
//            }

            R.id.btnAddToFavourite -> {
                view.context.apply {
                    dataModel.value?.let {
                        val isFav = it.isFav
                        it.isFav = !isFav
                        isFavMovie.postValue(!isFav)
                        updateVideoStreamCategory(it)
                    }
                }
            }
        }
    }

    /**
     * Update Video Stream Category
     */
    private fun updateVideoStreamCategory(model: VideoStreamCategory) {
        viewModelScope.launch {
            roomRepository.updateVideoStreamCategory(model)
        }
    }

    /**
     * Fetch Movie Info
     */
    fun getMovieInfo(currentUserModel: UserModel, videoId: String) {
        viewModelScope.launch {
            movieInfoData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiRepository.getMovieInfo(
                    currentUserModel,
                    ConstantUtil.VIDEO_INFO_ACTION,
                    videoId
                )
                    .let {
                        if (it.isSuccessful) {
                            movieInfoData.postValue(Resource.success(it.body()))
                        } else {
                            movieInfoData.postValue(Resource.error("URL is not valid", null))
                        }
                    }
            } else {
                movieInfoData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

}