package com.dhiman.iptv.dialog.loading_dialog

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dhiman.iptv.data.local.db.entity.*
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import com.dhiman.iptv.util.Resource
import com.dhiman.iptv.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

@HiltViewModel
class LoadingDialogViewModel @Inject constructor(
    private val apiRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val roomRepository: RoomRepository,
) : BaseViewModel() {

    //  var currentUserModel = sharedPrefs.getCurrentUser()

    val liveCategoriesData = MutableLiveData<Resource<List<LiveCategoryModel>>>()
    val videoCategoriesData = MutableLiveData<Resource<List<VideoCategoryModel>>>()
    val seriesCategoriesData = MutableLiveData<Resource<List<SeriesCategoryModel>>>()
    val epgData = MutableLiveData<Resource<List<EpgModel>>>()

    val liveStreamCategoriesData = MutableLiveData<Resource<List<LiveStreamCategory>>>()
    val videoStreamCategoriesData = MutableLiveData<Resource<List<VideoStreamCategory>>>()
    val seriesStreamCategoriesData = MutableLiveData<Resource<List<SeriesStreamCategory>>>()

    fun getAllData(context: Context) {
//        val myWork = OneTimeWorkRequest.Builder(FetchAllDataWorker::class.java)
//            .setInitialDelay(0, TimeUnit.SECONDS)
//            .build()
//        WorkManager.getInstance(context).enqueue(myWork)


        val work = OneTimeWorkRequestBuilder<FetchAllDataWorker>().build()
        WorkManager.getInstance(context).enqueue(work)
    }

    fun getAllTypeCategory(currentUserModel: UserModel) {
        viewModelScope.launch {
            liveCategoriesData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {

                /** Fetch Live Categories */
                apiRepository.getLiveCategories(currentUserModel, ConstantUtil.LIVE_ACTION).let {
                    try {
                        if (it.isSuccessful) {
                            liveCategoriesData.postValue(Resource.success(it.body()))
                        } else {
                            liveCategoriesData.postValue(Resource.error("URL is not valid", null))
                        }
                    } catch (e: Exception) {
                        Log.d("test_api_response", "getAllTypeCategory: ${e.localizedMessage}")
                    }

                    /** Fetch Videos Categories */
                    apiRepository.getVideoCategories(currentUserModel, ConstantUtil.VIDEO_ACTION)
                        .let {
                            if (it.isSuccessful) {
                                videoCategoriesData.postValue(Resource.success(it.body()))
                            } else {
                                videoCategoriesData.postValue(
                                    Resource.error(
                                        "URL is not valid",
                                        null
                                    )
                                )
                            }

                            /** Fetch Series Categories */
                            apiRepository.getSeriesCategories(
                                currentUserModel,
                                ConstantUtil.SERIES_ACTION
                            )
                                .let {
                                    if (it.isSuccessful) {
                                        seriesCategoriesData.postValue(Resource.success(it.body()))
                                    } else {
                                        seriesCategoriesData.postValue(
                                            Resource.error(
                                                "URL is not valid",
                                                null
                                            )
                                        )
                                    }

                                    /** Fetch Live Stream Categories */
                                    apiRepository.getLiveStreamCategories(
                                        currentUserModel,
                                        ConstantUtil.LIVE_STREAM_ACTION
                                    ).let {
                                        if (it.isSuccessful) {
                                            liveStreamCategoriesData.postValue(Resource.success(it.body()))
                                        } else {
                                            liveStreamCategoriesData.postValue(
                                                Resource.error(
                                                    "URL is not valid",
                                                    null
                                                )
                                            )
                                        }

                                        /** Fetch Video Stream Categories */
                                        apiRepository.getVideoStreamCategories(
                                            currentUserModel,
                                            ConstantUtil.VIDEO_STREAM_ACTION
                                        ).let {
                                            if (it.isSuccessful) {
                                                videoStreamCategoriesData.postValue(
                                                    Resource.success(
                                                        it.body()
                                                    )
                                                )
                                            } else {
                                                videoStreamCategoriesData.postValue(
                                                    Resource.error(
                                                        "URL is not valid",
                                                        null
                                                    )
                                                )
                                            }

                                            /** Fetch Series Stream Categories */
                                            apiRepository.getSeriesStreamCategories(
                                                currentUserModel,
                                                ConstantUtil.SERIES_STREAM_ACTION
                                            ).let {
                                                if (it.isSuccessful) {
                                                    seriesStreamCategoriesData.postValue(
                                                        Resource.success(
                                                            it.body()
                                                        )
                                                    )
                                                } else {
                                                    seriesStreamCategoriesData.postValue(
                                                        Resource.error(
                                                            "URL is not valid",
                                                            null
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                        }

                }

            } else {
                liveCategoriesData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun getAllTypeCategories(currentUserModel: UserModel) {
        viewModelScope.launch {
            liveCategoriesData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    coroutineScope {
                        val getVideoStreamCategoriesCall = async {
                            apiRepository.getVideoStreamCategories(
                                currentUserModel,
                                ConstantUtil.VIDEO_STREAM_ACTION
                            )
                        }
                        delay(500)
                       /* val getSeriesStreamCategoriesCall = async {
                            apiRepository.getSeriesStreamCategories(
                                currentUserModel,
                                ConstantUtil.SERIES_STREAM_ACTION
                            )
                        }*/
                        delay(500)
                        val getEPGCall = async {
                            apiRepository.getEPG(
                                currentUserModel
                            )
                        }
                        delay(500)
                      /*  val getLiveCategoriesCall = async {
                            apiRepository.getLiveCategories(
                                currentUserModel,
                                ConstantUtil.LIVE_ACTION
                            )
                        }*/
                        delay(500)
                        val getVideoCategoriesCall = async {
                            apiRepository.getVideoCategories(
                                currentUserModel,
                                ConstantUtil.VIDEO_ACTION
                            )
                        }
                        delay(500)
                       /* val getSeriesCategoriesCall = async {
                            apiRepository.getSeriesCategories(
                                currentUserModel,
                                ConstantUtil.SERIES_ACTION
                            )
                        }*/
                        delay(500)
/*
                        val getLiveStreamCategoriesCall = async {
                            apiRepository.getLiveStreamCategories(
                                currentUserModel,
                                ConstantUtil.LIVE_STREAM_ACTION
                            )
                        }
*/

                        val getVideoStreamCategoriesResponse = getVideoStreamCategoriesCall.await()
//                        val getSeriesStreamCategoriesResponse =
//                            getSeriesStreamCategoriesCall.await()
                //        val getLiveCategoriesResponse = getLiveCategoriesCall.await()
                        val getVideoCategoriesResponse = getVideoCategoriesCall.await()
                        val getEPGResponse =
                            getEPGCall.await()
                   //     val getSeriesCategoriesResponse = getSeriesCategoriesCall.await()
                   //     val getLiveStreamCategoriesResponse = getLiveStreamCategoriesCall.await()

//                        if (getLiveCategoriesResponse.isSuccessful) {
//                            liveCategoriesData.postValue(Resource.success(getLiveCategoriesResponse.body()))
//                        }

                        if (getVideoCategoriesResponse.isSuccessful) {
                            videoCategoriesData.postValue(
                                Resource.success(
                                    getVideoCategoriesResponse.body()
                                )
                            )
                        }

//                        if (getSeriesCategoriesResponse.isSuccessful) {
//                            seriesCategoriesData.postValue(
//                                Resource.success(
//                                    getSeriesCategoriesResponse.body()
//                                )
//                            )
//                        }

//                        if (getLiveStreamCategoriesResponse.isSuccessful) {
//                            liveStreamCategoriesData.postValue(
//                                Resource.success(
//                                    getLiveStreamCategoriesResponse.body()
//                                )
//                            )
//                        }

                        if (getVideoStreamCategoriesResponse.isSuccessful) {
                            videoStreamCategoriesData.postValue(
                                Resource.success(
                                    getVideoStreamCategoriesResponse.body()
                                )
                            )
                        }

//                        if (getSeriesStreamCategoriesResponse.isSuccessful) {
//                            seriesStreamCategoriesData.postValue(
//                                Resource.success(
//                                    getSeriesStreamCategoriesResponse.body()
//                                )
//                            )
//                        }

                        // ✅ Safely handle huge EPG XML
                        if (getEPGResponse.isSuccessful) {
                            getEPGResponse.body()?.use { body ->
                                val inputStream = body.byteStream()
                                val reader = BufferedReader(InputStreamReader(inputStream))
                                val builder = StringBuilder()
                                var line: String?

                                // Read XML in chunks (not all at once)
                                var lineCount = 0
                                while (reader.readLine().also { line = it } != null) {
                                    builder.append(line)
                                    lineCount++

                                    // (Optional) limit to avoid loading entire huge XML during debug
                                    // if (lineCount > 10000) break
                                }

                                val xmlString = builder.toString()
                                Log.d("EPG_XML", xmlString.take(500)) // preview first 500 chars

                                // ✅ Convert XML → JSON
                                val xmlToJson = XmlToJson.Builder(xmlString).build()
                                val jsonObject = xmlToJson.toJson()
                                jsonObject?.let {
                                    val dataArrayList = ArrayList<EpgModel>()

                                    val tvJsonObject = it.getJSONObject("tv")
                                    val programJsonArray = tvJsonObject.getJSONArray("programme")
                                    for (x in 0 until programJsonArray.length()) {
                                        val dataObject = programJsonArray.getJSONObject(x)
                                        val epgModel = EpgModel()
                                        epgModel.channel = dataObject.optString("channel")
                                        epgModel.desc = dataObject.optString("desc")
                                        epgModel.start = dataObject.optString("start")
                                        epgModel.stop = dataObject.optString("stop")
                                        epgModel.title = dataObject.optString("title")

                                        dataArrayList.add(epgModel)
                                    }
                                    epgData.postValue(Resource.success(dataArrayList))
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    liveCategoriesData.postValue(Resource.error(e.localizedMessage, null))
                }
            } else {
                liveCategoriesData.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    fun deleteAllCategoriesFromRoomDatabase() {
        viewModelScope.launch {
            roomRepository.deleteAllLiveCategories()
            roomRepository.deleteAllLiveStreamCategories()
            roomRepository.deleteAllVideoCategories()
            roomRepository.deleteAllVideoStreamCategories()
            roomRepository.deleteAllSeriesCategories()
            roomRepository.deleteAllSeriesStreamCategories()
            roomRepository.deleteAllEpg()
        }
    }

    fun insertAllLiveCategoriesToRoomDatabase(dataList: List<LiveCategoryModel>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllLiveCategories(it) }
        }
    }

    fun insertAllVideoCategoriesToRoomDatabase(dataList: List<VideoCategoryModel>?) {
        viewModelScope.launch {
            dataList?.let {
                roomRepository.insertAllVideoCategories(it)
            }
        }
    }

    fun insertAllSeriesCategoriesToRoomDatabase(dataList: List<SeriesCategoryModel>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllSeriesCategories(it) }
        }
    }

    fun insertAllLiveStreamCategoriesToRoomDatabase(dataList: List<LiveStreamCategory>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllLiveStreamCategories(it) }
        }
    }

    fun insertAllVideoStreamCategoriesToRoomDatabase(dataList: List<VideoStreamCategory>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllVideoStreamCategories(it) }
        }
    }

    fun insertAllSeriesStreamCategoriesToRoomDatabase(dataList: List<SeriesStreamCategory>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllSeriesStreamCategories(it) }
        }
    }

    fun insertAllEpgToRoomDatabase(dataList: List<EpgModel>?) {
        viewModelScope.launch {
            dataList?.let { roomRepository.insertAllEpg(it) }
        }
    }

}