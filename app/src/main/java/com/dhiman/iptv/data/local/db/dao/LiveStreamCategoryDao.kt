package com.dhiman.iptv.data.local.db.dao

import androidx.room.*
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory

@Dao
interface LiveStreamCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLiveStreamCategories(dataList: List<LiveStreamCategory>)

    @Update
    suspend fun updateLiveStreamCategory(model: LiveStreamCategory)

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY")
    suspend fun getAllLiveStreamCategories(): List<LiveStreamCategory>

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE categoryId= :reqCategoryId LIMIT :limit OFFSET :offset")
    suspend fun getSelectedLiveStreamCategoriesPagination(
        reqCategoryId: String,
        limit: Int,
        offset: Int
    ): List<LiveStreamCategory>

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE isFav = 1 LIMIT :limit OFFSET :offset")
    suspend fun getSelectedLiveStreamCategoriesPaginationFav(
        limit: Int,
        offset: Int
    ): List<LiveStreamCategory>

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE categoryId= :reqCategoryId AND name LIKE :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getSelectedLiveStreamCategoriesPaginationWithSearch(
        reqCategoryId: String,
        searchQuery: String, limit: Int, offset: Int
    ): List<LiveStreamCategory>

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE isFav = 1 AND name LIKE :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getSelectedLiveStreamCategoriesPaginationWithSearchFav(
        searchQuery: String, limit: Int, offset: Int
    ): List<LiveStreamCategory>

//    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE categoryId= :reqCategoryId ORDER BY name")
//    fun getSelectedLiveStreamCategoriesPagination(reqCategoryId: String): PagingSource<Int, LiveStreamCategory>
//
//    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE categoryId= :reqCategoryId AND name LIKE :searchQuery ORDER BY name")
//    fun getSelectedLiveStreamCategoriesPaginationWithSearch(
//        reqCategoryId: String,
//        searchQuery: String
//    ): PagingSource<Int, LiveStreamCategory>

    //    @Query("SELECT l.* FROM LIVE_STREAM_CATEGORY as l LEFT JOIN EPG as e ON l.epgChannelId=e.channel WHERE l.categoryId= :reqCategoryId AND e.id > 0 GROUP BY l.epgChannelId")
    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE categoryId= :reqCategoryId")
    suspend fun getSelectedLiveStreamCategories(reqCategoryId: String): List<LiveStreamCategory>

    @Query("DELETE FROM LIVE_STREAM_CATEGORY")
    suspend fun deleteAllLiveStreamCategories()


    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE tvArchive = :reqTvArchive AND categoryId= :reqCategoryId")
    suspend fun getCatchUpStreamCategoriesPagination(
        reqCategoryId: String,
        reqTvArchive: String
    ): List<LiveStreamCategory>

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE isFav = 1 AND tvArchive = :reqTvArchive")
    suspend fun getCatchUpStreamCategoriesPaginationFav(
        reqTvArchive: String
    ): List<LiveStreamCategory>

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE categoryId= :reqCategoryId AND tvArchive = :reqTvArchive AND name LIKE :searchQuery")
    suspend fun getCatchUpStreamCategoriesPaginationWithSearch(
        reqCategoryId: String,
        searchQuery: String,
        reqTvArchive: String
    ): List<LiveStreamCategory>

    @Query("SELECT * FROM LIVE_STREAM_CATEGORY WHERE isFav = 1 AND tvArchive = :reqTvArchive AND name LIKE :searchQuery")
    suspend fun getCatchUpStreamCategoriesPaginationWithSearchFav(
        searchQuery: String,
        reqTvArchive: String
    ): List<LiveStreamCategory>

}