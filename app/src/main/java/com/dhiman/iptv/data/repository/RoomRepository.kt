package com.dhiman.iptv.data.repository

import com.dhiman.iptv.data.local.db.AppDatabase
import com.dhiman.iptv.data.local.db.entity.*
import com.dhiman.iptv.util.ConstantUtil
import javax.inject.Inject

class RoomRepository @Inject constructor(private val appDatabase: AppDatabase) {

    suspend fun insertAllLiveCategories(dataList: List<LiveCategoryModel>) {
        return appDatabase.liveCategoryDao().insertAllLiveCategories(dataList)
    }

    suspend fun getAllLiveCategories(): List<LiveCategoryModel> {
        return appDatabase.liveCategoryDao().getAllLiveCategories()
    }

    suspend fun deleteAllLiveCategories() {
        return appDatabase.liveCategoryDao().deleteAllLiveCategories()
    }

    suspend fun insertAllVideoCategories(dataList: List<VideoCategoryModel>) {
        return appDatabase.videoCategoryDao().insertAllVideoCategories(dataList)
    }

    suspend fun getAllVideoCategories(): List<VideoCategoryModel> {
        return appDatabase.videoCategoryDao().getAllVideoCategories()
    }

    suspend fun deleteAllVideoCategories() {
        return appDatabase.videoCategoryDao().deleteAllVideoCategories()
    }

    suspend fun insertAllSeriesCategories(dataList: List<SeriesCategoryModel>) {
        return appDatabase.seriesCategoryDao().insertAllSeriesCategories(dataList)
    }

    suspend fun getAllSeriesCategories(): List<SeriesCategoryModel> {
        return appDatabase.seriesCategoryDao().getAllSeriesCategories()
    }

    suspend fun deleteAllSeriesCategories() {
        return appDatabase.seriesCategoryDao().deleteAllSeriesCategories()
    }

    suspend fun insertAllLiveStreamCategories(dataList: List<LiveStreamCategory>) {
        return appDatabase.liveStreamCategoryDao().insertAllLiveStreamCategories(dataList)
    }

    suspend fun updateLiveStreamCategory(model: LiveStreamCategory) {
        return appDatabase.liveStreamCategoryDao().updateLiveStreamCategory(model)
    }

    suspend fun getAllLiveStreamCategories(): List<LiveStreamCategory> {
        return appDatabase.liveStreamCategoryDao().getAllLiveStreamCategories()
    }

    suspend fun getSelectedLiveStreamCategories(reqCategoryId: String): List<LiveStreamCategory> {
        return appDatabase.liveStreamCategoryDao()
            .getSelectedLiveStreamCategories(reqCategoryId)
    }

    suspend fun getSelectedLiveStreamCategoriesPagination(
        reqCategoryId: String,
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<LiveStreamCategory> {
        return if (searchQuery.trim().isEmpty()) {
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.liveStreamCategoryDao()
                    .getSelectedLiveStreamCategoriesPaginationFav(limit, offset)
            } else {
                appDatabase.liveStreamCategoryDao()
                    .getSelectedLiveStreamCategoriesPagination(reqCategoryId, limit, offset)
            }
        } else {
            val searchTextQuery = "%$searchQuery%"
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.liveStreamCategoryDao()
                    .getSelectedLiveStreamCategoriesPaginationWithSearchFav(
                        searchTextQuery,
                        limit,
                        offset
                    )
            } else {
                appDatabase.liveStreamCategoryDao()
                    .getSelectedLiveStreamCategoriesPaginationWithSearch(
                        reqCategoryId,
                        searchTextQuery, limit, offset
                    )
            }
        }
    }

    suspend fun deleteAllLiveStreamCategories() {
        return appDatabase.liveStreamCategoryDao().deleteAllLiveStreamCategories()
    }

    suspend fun insertAllVideoStreamCategories(dataList: List<VideoStreamCategory>) {
        return appDatabase.videoStreamCategoryDao().insertAllVideoStreamCategories(dataList)
    }

    suspend fun updateVideoStreamCategory(model: VideoStreamCategory) {
        return appDatabase.videoStreamCategoryDao().updateVideoStreamCategory(model)
    }

    suspend fun getAllVideoStreamCategories(): List<VideoStreamCategory> {
        return appDatabase.videoStreamCategoryDao().getAllVideoStreamCategories()
    }

    suspend fun getSelectedVideoStreamCategories(
        reqCategoryId: String,
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<VideoStreamCategory> {
        return if (searchQuery.trim().isEmpty()) {
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPaginationFav(limit, offset)
            } else {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPagination(reqCategoryId, limit, offset)
            }
        } else {
            val searchTextQuery = "%$searchQuery%"
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPaginationWithSearchFav(
                        searchTextQuery,
                        limit, offset
                    )
            } else {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPaginationWithSearch(
                        reqCategoryId,
                        searchTextQuery,
                        limit, offset
                    )
            }
        }
    }

    suspend fun getSelectedVideoStreamCategoriesPagination(
        reqCategoryId: String,
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<VideoStreamCategory> {
        return if (searchQuery.trim().isEmpty()) {
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPaginationFav(limit, offset)
            } else {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPagination(reqCategoryId, limit, offset)
            }
        } else {
            val searchTextQuery = "%$searchQuery%"
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPaginationWithSearchFav(
                        searchTextQuery,
                        limit, offset
                    )
            } else {
                appDatabase.videoStreamCategoryDao()
                    .getSelectedVideoStreamCategoriesPaginationWithSearch(
                        reqCategoryId,
                        searchTextQuery,
                        limit, offset
                    )
            }
        }
    }

    suspend fun deleteAllVideoStreamCategories() {
        return appDatabase.videoStreamCategoryDao().deleteAllVideoStreamCategories()
    }

    suspend fun insertAllSeriesStreamCategories(dataList: List<SeriesStreamCategory>) {
        return appDatabase.seriesStreamCategoryDao().insertAllSeriesStreamCategories(dataList)
    }

    suspend fun updateSeriesStreamCategory(model: SeriesStreamCategory) {
        return appDatabase.seriesStreamCategoryDao().updateSeriesStreamCategory(model)
    }

    suspend fun getAllSeriesStreamCategories(): List<SeriesStreamCategory> {
        return appDatabase.seriesStreamCategoryDao().getAllSeriesStreamCategories()
    }

    suspend fun getSelectedSeriesStreamCategoriesPagination(
        reqCategoryId: String,
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<SeriesStreamCategory> {
        return if (searchQuery.trim().isEmpty()) {
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.seriesStreamCategoryDao()
                    .getSelectedSeriesStreamCategoriesPaginationFav(limit, offset)
            } else {
                appDatabase.seriesStreamCategoryDao()
                    .getSelectedSeriesStreamCategoriesPagination(reqCategoryId, limit, offset)
            }
        } else {
            val searchTextQuery = "%$searchQuery%"
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.seriesStreamCategoryDao()
                    .getSelectedSeriesStreamCategoriesPaginationWithSearchFav(
                        searchTextQuery, limit, offset
                    )
            } else {
                appDatabase.seriesStreamCategoryDao()
                    .getSelectedSeriesStreamCategoriesPaginationWithSearch(
                        reqCategoryId,
                        searchTextQuery, limit, offset
                    )
            }
        }
    }

    suspend fun deleteAllSeriesStreamCategories() {
        return appDatabase.seriesStreamCategoryDao().deleteAllSeriesStreamCategories()
    }

    suspend fun insertAllEpg(dataList: List<EpgModel>) {
        return appDatabase.epgDao().insertAllEpg(dataList)
    }

    suspend fun getSelectedEpg(reqEpgChannelId: String): List<EpgModel> {
        return appDatabase.epgDao().getSelectedEpg(reqEpgChannelId)
    }

    suspend fun deleteAllEpg() {
        return appDatabase.epgDao().deleteAllEpg()
    }

    suspend fun insertUser(model: UserDataModel) {
        return appDatabase.userDao().insertUser(model)
    }

    suspend fun getAllUsers(): List<UserDataModel> {
        return appDatabase.userDao().getAllUsers()
    }

    suspend fun updateUser(model: UserDataModel) {
        return appDatabase.userDao().updateUser(model)
    }

    suspend fun deleteUser(model: UserDataModel) {
        return appDatabase.userDao().deleteUser(model)
    }

    suspend fun insertAllCommonCategories(dataList: List<CommonCategoryModel>) {
        return appDatabase.commonCategoryDao().insertAllCommonCategories(dataList)
    }

    suspend fun getAllCommonCategories(): List<CommonCategoryModel> {
        return appDatabase.commonCategoryDao().getAllCommonCategories()
    }

    suspend fun deleteAllCommonCategories() {
        return appDatabase.commonCategoryDao().deleteAllCommonCategories()
    }

    suspend fun insertAllM3UPlaylist(dataList: List<M3UPlaylistModel>) {
        return appDatabase.m3uPlaylistDao().insertAllM3UPlaylist(dataList)
    }

    suspend fun insertAllM3UPlaylistItems(dataList: List<M3UItemModel>) {
        return appDatabase.m3uPlaylistDao().insertAllM3UPlaylistItems(dataList)
    }

    suspend fun insertM3UPlaylist(model: M3UPlaylistModel) {
        return appDatabase.m3uPlaylistDao().insertM3UPlaylist(model)
    }

    suspend fun getAllM3UPlaylist(): List<M3UPlaylistModel> {
        return appDatabase.m3uPlaylistDao().getAllM3UPlaylist()
    }

    suspend fun getSelectedM3UPlaylist(
        reqCategoryId: String,
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<M3UItemModel> {
        return if (searchQuery.trim().isEmpty()) {
            if (reqCategoryId == "null") {
                appDatabase.m3uPlaylistDao()
                    .getSelectedM3UPlaylistNULLPagination(limit, offset)
            } else if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.m3uPlaylistDao()
                    .getSelectedM3UPlaylistPaginationFav()
            } else {
                appDatabase.m3uPlaylistDao()
                    .getSelectedM3UPlaylistPagination(reqCategoryId)
            }
        } else {
            val searchTextQuery = "%$searchQuery%"
            if (reqCategoryId == "null") {
                appDatabase.m3uPlaylistDao()
                    .getSelectedM3UPlaylistPaginationNULLWithSearch(searchTextQuery, limit, offset)
            } else if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.m3uPlaylistDao()
                    .getSelectedM3UPlaylistPaginationWithSearchFav(
                        searchTextQuery
                    )
            } else {
                appDatabase.m3uPlaylistDao()
                    .getSelectedM3UPlaylistPaginationWithSearch(
                        reqCategoryId,
                        searchTextQuery
                    )
            }
        }
    }

    suspend fun updateM3UPlaylist(model: M3UItemModel) {
        return appDatabase.m3uPlaylistDao().updateM3UPlaylist(model)
    }

    suspend fun deleteAllM3UPlaylist() {
        return appDatabase.m3uPlaylistDao().deleteAllM3UPlaylist()
    }

    suspend fun deleteAllM3UPlaylistItems() {
        return appDatabase.m3uPlaylistDao().deleteAllM3UPlaylistItems()
    }


    suspend fun getCatchUpStreamCategoriesPagination(
        reqCategoryId: String,
        searchQuery: String
    ): List<LiveStreamCategory> {
        return if (searchQuery.trim().isEmpty()) {
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.liveStreamCategoryDao()
                    .getCatchUpStreamCategoriesPaginationFav("1")
            } else {
                appDatabase.liveStreamCategoryDao()
                    .getCatchUpStreamCategoriesPagination(reqCategoryId, "1")
            }
        } else {
            val searchTextQuery = "%$searchQuery%"
            if (reqCategoryId.equals(ConstantUtil.FAV_CATEGORY_ID, true)) {
                appDatabase.liveStreamCategoryDao()
                    .getCatchUpStreamCategoriesPaginationWithSearchFav(searchTextQuery, "1")
            } else {
                appDatabase.liveStreamCategoryDao()
                    .getCatchUpStreamCategoriesPaginationWithSearch(
                        reqCategoryId,
                        searchTextQuery,
                        "1"
                    )
            }
        }
    }


}