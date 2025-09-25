package com.dhiman.iptv.data.api

import com.dhiman.iptv.data.local.db.entity.*
import com.dhiman.iptv.data.model.MovieInfoModel
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.model.catchUp.CatchUpModel
import okhttp3.ResponseBody
import retrofit2.Response

interface ApiHelper {
    suspend fun userAuth(baseUrl: String, username: String, password: String)
            : Response<UserAuth>

    suspend fun getLiveCategories(currentUserModel: UserModel, action: String)
            : Response<List<LiveCategoryModel>>

    suspend fun getVideosCategories(currentUserModel: UserModel, action: String)
            : Response<List<VideoCategoryModel>>

    suspend fun getSeriesCategories(currentUserModel: UserModel, action: String)
            : Response<List<SeriesCategoryModel>>

    suspend fun getLiveStreamCategories(currentUserModel: UserModel, action: String)
            : Response<List<LiveStreamCategory>>

    suspend fun getVideosStreamCategories(currentUserModel: UserModel, action: String)
            : Response<List<VideoStreamCategory>>

    suspend fun getSeriesStreamCategories(currentUserModel: UserModel, action: String)
            : Response<List<SeriesStreamCategory>>

    suspend fun getMovieInfo(currentUserModel: UserModel, action: String, vodId: String)
            : Response<MovieInfoModel>

    suspend fun getSeriesInfo(currentUserModel: UserModel, action: String, seriesId: String)
            : Response<String>

    suspend fun getEPG(currentUserModel: UserModel): Response<String>

    suspend fun getCatchUpChannelPrograms(
        currentUserModel: UserModel,
        action: String,
        vodId: String
    ): Response<CatchUpModel>

    suspend fun downloadFile(fileUrl: String): Response<ResponseBody>

}