package com.dhiman.iptv.data.api

import com.dhiman.iptv.data.local.db.entity.*
import com.dhiman.iptv.data.model.MovieInfoModel
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.model.catchUp.CatchUpModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun userAuth(
        @Url baseUrl: String,
        @Query("username") url: String,
        @Query("password") clientId: String
    ): Response<UserAuth>

    @GET
    suspend fun getLiveCategories(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
    ): Response<List<LiveCategoryModel>>

    @GET
    suspend fun getVideosCategories(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
    ): Response<List<VideoCategoryModel>>

    @GET
    suspend fun getSeriesCategories(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
    ): Response<List<SeriesCategoryModel>>

    @GET
    suspend fun getLiveStreamCategories(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
    ): Response<List<LiveStreamCategory>>

    @GET
    suspend fun getVideoStreamCategories(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
    ): Response<List<VideoStreamCategory>>

    @GET
    suspend fun getSeriesStreamCategories(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
    ): Response<List<SeriesStreamCategory>>


    @GET
    suspend fun getMovieInfo(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
        @Query("vod_id") vodId: String,
    ): Response<MovieInfoModel>

    @GET
    suspend fun getSeriesInfo(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
        @Query("series_id") seriesId: String,
    ): Response<String>

    @GET
    suspend fun getEPG(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
    ): Response<String>

    @GET
    suspend fun getCatchUpChannelPrograms(
        @Url baseUrl: String,
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String,
        @Query("stream_id") seriesId: String,
    ): Response<CatchUpModel>

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String): Response<ResponseBody>


}