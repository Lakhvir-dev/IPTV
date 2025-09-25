package com.dhiman.iptv.activity.series_program

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dhiman.iptv.R
import com.dhiman.iptv.data.local.db.entity.SeriesStreamCategory
import com.dhiman.iptv.data.model.SeriesInfoModel
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SeriesDetailsViewModel @Inject constructor(
    private val apiRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val roomRepository: RoomRepository
) : BaseViewModel() {

    val isFocusOnBack = ObservableField(true)
    val isFocusOnWatchTrailer = ObservableField(false)
    val isFocusOnLeftArrow = ObservableField(false)
    val isFocusOnRightArrow = ObservableField(false)
    val seriesInfoData = MutableLiveData<Resource<SeriesInfoModel>>()
    val movieYoutubeLink = MutableLiveData<String>()
    val dataModel = MutableLiveData<SeriesStreamCategory>()
    val isFavSeries = MutableLiveData<Boolean>()
    // val movieFullLink = MutableLiveData<String>()

    fun onFocusChange(view: View, value: Boolean) {
        when (view.id) {
            R.id.ivBack -> {
                isFocusOnBack.set(value)
            }

//            R.id.llWatchTrailer -> {
//                isFocusOnWatchTrailer.set(value)
//            }
//
//            R.id.ivLeftChannel -> {
//                isFocusOnLeftArrow.set(value)
//            }
//
//            R.id.ivRightChannel -> {
//                isFocusOnRightArrow.set(value)
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
                        isFavSeries.postValue(!isFav)
                        updateSeriesStreamCategory(it)
                    }
                }
            }
        }
    }

    /**
     * Update Series Data
     */
    private fun updateSeriesStreamCategory(model: SeriesStreamCategory) {
        viewModelScope.launch {
            roomRepository.updateSeriesStreamCategory(model)
        }
    }

    /**
     * Fetch Series Info
     */
    fun getSeriesInfo(currentUserModel: UserModel, seriesId: String) {
        viewModelScope.launch {
            seriesInfoData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                apiRepository.getSeriesInfo(
                    currentUserModel,
                    ConstantUtil.SERIES_INFO_ACTION,
                    seriesId
                )
                    .let {
                        if (it.isSuccessful) {
                            it.body()?.let { responseBody ->
                                try {
                                    val seriesInfoModel = SeriesInfoModel()
                                    val gson = Gson()
                                    val jsonObject = JSONObject(responseBody)

                                    val seasonsJSONArray = jsonObject.getJSONArray("seasons")
                                    val seasonsList = gson.fromJson(
                                        seasonsJSONArray.toString(),
                                        object :
                                            TypeToken<ArrayList<SeriesInfoModel.Season>>() {}.type
                                    ) as ArrayList<SeriesInfoModel.Season>
                                    seriesInfoModel.seasons = seasonsList

                                    val infoJSONObject = jsonObject.getJSONObject("info")
                                    val infoModel = gson.fromJson(
                                        infoJSONObject.toString(),
                                        object :
                                            TypeToken<SeriesInfoModel.SeriesInfo>() {}.type
                                    ) as SeriesInfoModel.SeriesInfo
                                    seriesInfoModel.info = infoModel

                                    val episodesJSONObject = jsonObject.getJSONObject("episodes")
                                    val i = episodesJSONObject.keys()

                                    // val singleEpisode = SeriesInfoModel.SingleEpisode()
                                    // val episodes: HashMap<String,  ArrayList<SeriesInfoModel.Episodes>> = HashMap()
                                    while (i.hasNext()) {
                                        val indexValue = i.next().toString()

                                        val singleSeasonEpisode =
                                            episodesJSONObject.getJSONArray(indexValue)
                                        val episodesList = gson.fromJson(
                                            singleSeasonEpisode.toString(),
                                            object :
                                                TypeToken<ArrayList<SeriesInfoModel.Episodes>>() {}.type
                                        ) as ArrayList<SeriesInfoModel.Episodes>
                                        //   episodes[indexValue] = episodesList
//                                        singleEpisode.key = indexValue
//                                        singleEpisode.listOfEpisodes = episodesList

                                        seriesInfoModel.episodes.addAll(episodesList)
                                    }


                                    seriesInfoData.postValue(Resource.success(seriesInfoModel))
                                } catch (e: Exception) {
                                    seriesInfoData.postValue(
                                        Resource.error(
                                            e.message.toString(),
                                            null
                                        )
                                    )
                                }
                            }
                        } else {
                            seriesInfoData.postValue(Resource.error("URL is not valid!", null))
                        }
                    }
            } else {
                seriesInfoData.postValue(Resource.error("No internet connection!", null))
            }
        }
    }

}