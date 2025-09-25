package com.dhiman.iptv.data.repository

import com.dhiman.iptv.data.api.ApiHelper
import com.dhiman.iptv.data.model.UserModel
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {

    suspend fun userAuth(baseUrl: String, username: String, password: String) =
        apiHelper.userAuth(baseUrl, username, password)

    suspend fun getLiveCategories(
        currentUserModel: UserModel,
        action: String
    ) =
        apiHelper.getLiveCategories(currentUserModel, action)

    suspend fun getVideoCategories(
        currentUserModel: UserModel,
        action: String
    ) =
        apiHelper.getVideosCategories(currentUserModel, action)

    suspend fun getSeriesCategories(
        currentUserModel: UserModel,
        action: String
    ) =
        apiHelper.getSeriesCategories(currentUserModel, action)

    suspend fun getLiveStreamCategories(
        currentUserModel: UserModel,
        action: String
    ) =
        apiHelper.getLiveStreamCategories(currentUserModel, action)

    suspend fun getVideoStreamCategories(
        currentUserModel: UserModel,
        action: String
    ) =
        apiHelper.getVideosStreamCategories(currentUserModel, action)

    suspend fun getSeriesStreamCategories(
        currentUserModel: UserModel,
        action: String
    ) =
        apiHelper.getSeriesStreamCategories(currentUserModel, action)

    suspend fun getMovieInfo(
        currentUserModel: UserModel,
        action: String,
        vodId: String
    ) =
        apiHelper.getMovieInfo(currentUserModel, action, vodId)

    suspend fun getSeriesInfo(
        currentUserModel: UserModel,
        action: String,
        seriesId: String
    ) =
        apiHelper.getSeriesInfo(currentUserModel, action, seriesId)

    suspend fun getEPG(
        currentUserModel: UserModel
    ) =
        apiHelper.getEPG(currentUserModel)

    suspend fun getCatchUpChannelPrograms(
        currentUserModel: UserModel,
        action: String,
        vodId: String
    ) =
        apiHelper.getCatchUpChannelPrograms(currentUserModel, action, vodId)

    suspend fun getChannelEPGPrograms(
        currentUserModel: UserModel,
        action: String,
        vodId: String
    ) =
        apiHelper.getCatchUpChannelPrograms(currentUserModel, action, vodId)

    suspend fun downloadFile(fileUrl: String) = apiHelper.downloadFile(fileUrl)

}