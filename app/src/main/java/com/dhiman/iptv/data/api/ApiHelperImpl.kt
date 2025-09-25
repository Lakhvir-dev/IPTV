package com.dhiman.iptv.data.api

import com.dhiman.iptv.data.local.db.entity.*
import com.dhiman.iptv.data.model.MovieInfoModel
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.model.UserModel
import com.dhiman.iptv.data.model.catchUp.CatchUpModel
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun userAuth(
        baseUrl: String,
        username: String,
        password: String
    ): Response<UserAuth> =
        apiService.userAuth("$baseUrl/player_api.php", username, password)

    override suspend fun getLiveCategories(
        currentUserModel: UserModel,
        action: String
    ): Response<List<LiveCategoryModel>> =
        apiService.getLiveCategories(
            currentUserModel.mainUrl + "/player_api.php",
            currentUserModel.userName,
            currentUserModel.password,
            action
        )

    override suspend fun getVideosCategories(
        currentUserModel: UserModel,
        action: String
    ): Response<List<VideoCategoryModel>> =
        apiService.getVideosCategories(
            currentUserModel.mainUrl + "/player_api.php",
            currentUserModel.userName,
            currentUserModel.password,
            action
        )

    override suspend fun getSeriesCategories(
        currentUserModel: UserModel,
        action: String
    ): Response<List<SeriesCategoryModel>> =
        apiService.getSeriesCategories(
            currentUserModel.mainUrl + "/player_api.php",
            currentUserModel.userName,
            currentUserModel.password,
            action
        )

    override suspend fun getLiveStreamCategories(
        currentUserModel: UserModel,
        action: String
    ): Response<List<LiveStreamCategory>> = apiService.getLiveStreamCategories(
        currentUserModel.mainUrl + "/player_api.php",
        currentUserModel.userName,
        currentUserModel.password,
        action
    )

    override suspend fun getVideosStreamCategories(
        currentUserModel: UserModel,
        action: String
    ): Response<List<VideoStreamCategory>> = apiService.getVideoStreamCategories(
        currentUserModel.mainUrl + "/player_api.php",
        currentUserModel.userName,
        currentUserModel.password,
        action
    )

    override suspend fun getSeriesStreamCategories(
        currentUserModel: UserModel,
        action: String
    ): Response<List<SeriesStreamCategory>> = apiService.getSeriesStreamCategories(
        currentUserModel.mainUrl + "/player_api.php",
        currentUserModel.userName,
        currentUserModel.password,
        action
    )

    override suspend fun getMovieInfo(
        currentUserModel: UserModel,
        action: String,
        vodId: String
    ): Response<MovieInfoModel> = apiService.getMovieInfo(
        currentUserModel.mainUrl + "/player_api.php",
        currentUserModel.userName,
        currentUserModel.password,
        action,
        vodId
    )

    override suspend fun getSeriesInfo(
        currentUserModel: UserModel,
        action: String,
        seriesId: String
    ): Response<String> = apiService.getSeriesInfo(
        currentUserModel.mainUrl + "/player_api.php",
        currentUserModel.userName,
        currentUserModel.password,
        action,
        seriesId
    )

    override suspend fun getEPG(
        currentUserModel: UserModel
    ): Response<String> = apiService.getEPG(
        currentUserModel.mainUrl + "/xmltv.php",
        currentUserModel.userName,
        currentUserModel.password
    )

    override suspend fun getCatchUpChannelPrograms(
        currentUserModel: UserModel,
        action: String,
        vodId: String
    ): Response<CatchUpModel> = apiService.getCatchUpChannelPrograms(
        currentUserModel.mainUrl + "/player_api.php",
        currentUserModel.userName,
        currentUserModel.password,
        action,
        vodId
    )

    override suspend fun downloadFile(fileUrl: String): Response<ResponseBody> =
        apiService.downloadFile(fileUrl)


//    override suspend fun getCatchUpChannelPrograms(
//        currentUserModel: UserModel,
//        action: String,
//        vodId: String
//    ): Response<List<CatchUpModel>> = apiService.getCatchUpChannelPrograms(
//        currentUserModel.mainUrl + "/player_api.php",
//        currentUserModel.userName,
//        currentUserModel.password,
//        action,
//        vodId
//    )

}