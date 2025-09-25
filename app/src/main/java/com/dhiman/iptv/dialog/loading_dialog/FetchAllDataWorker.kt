package com.dhiman.iptv.dialog.loading_dialog

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.dhiman.iptv.data.local.db.entity.EpgModel
import com.dhiman.iptv.data.local.prefs.SharedPrefs
import com.dhiman.iptv.data.repository.MainRepository
import com.dhiman.iptv.data.repository.RoomRepository
import com.dhiman.iptv.util.ConstantUtil
import com.dhiman.iptv.util.NetworkHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import kotlinx.coroutines.coroutineScope


@HiltWorker
class FetchAllDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val apiRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    private val roomRepository: RoomRepository,
    private val sharedPrefs: SharedPrefs
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result = coroutineScope {
        val currentUserModel = sharedPrefs.getCurrentUser()

        try {
            val videoStreamCategories = networkHelper.retrofitErrorHandler(
                apiRepository.getVideoStreamCategories(
                    currentUserModel,
                    ConstantUtil.VIDEO_STREAM_ACTION
                )
            )
            roomRepository.insertAllVideoStreamCategories(videoStreamCategories)

            val seriesStreamCategories = networkHelper.retrofitErrorHandler(
                apiRepository.getSeriesStreamCategories(
                    currentUserModel,
                    ConstantUtil.SERIES_STREAM_ACTION
                )
            )
            roomRepository.insertAllSeriesStreamCategories(seriesStreamCategories)

            val liveStreamCategories = networkHelper.retrofitErrorHandler(
                apiRepository.getLiveCategories(
                    currentUserModel,
                    ConstantUtil.LIVE_ACTION
                )
            )
            roomRepository.insertAllLiveCategories(liveStreamCategories)

            val getEPGCall = networkHelper.retrofitErrorHandler(
                apiRepository.getEPG(
                    currentUserModel
                )
            )

            val getVideoCategoriesCall = networkHelper.retrofitErrorHandler(
                apiRepository.getVideoCategories(
                    currentUserModel,
                    ConstantUtil.VIDEO_ACTION
                )
            )
            roomRepository.insertAllVideoCategories(getVideoCategoriesCall)

            val getSeriesCategoriesCall = networkHelper.retrofitErrorHandler(
                apiRepository.getSeriesCategories(
                    currentUserModel,
                    ConstantUtil.SERIES_ACTION
                )
            )
            roomRepository.insertAllSeriesCategories(getSeriesCategoriesCall)

            val getLiveStreamCategoriesCall = networkHelper.retrofitErrorHandler(
                apiRepository.getLiveStreamCategories(
                    currentUserModel,
                    ConstantUtil.LIVE_STREAM_ACTION
                )
            )
            roomRepository.insertAllLiveStreamCategories(getLiveStreamCategoriesCall)


            val xmlToJson = XmlToJson.Builder(getEPGCall).build()
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
                roomRepository.insertAllEpg(dataArrayList)
            }


        } catch (e: Exception) {

        }

        Result.success()
    }

}