package com.dhiman.iptv.data.local.db.dao

import androidx.room.*
import com.dhiman.iptv.data.local.db.entity.VideoStreamCategory

@Dao
interface VideoStreamCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVideoStreamCategories(dataList: List<VideoStreamCategory>)

    @Update
    suspend fun updateVideoStreamCategory(model: VideoStreamCategory)

    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY")
    suspend fun getAllVideoStreamCategories(): List<VideoStreamCategory>

    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY WHERE categoryId= :reqCategoryId")
    suspend fun getSelectedVideoStreamCategories(reqCategoryId: String): List<VideoStreamCategory>

    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY WHERE categoryId= :reqCategoryId LIMIT :limit OFFSET :offset")
    suspend fun getSelectedVideoStreamCategoriesPagination(reqCategoryId: String, limit: Int, offset: Int): List<VideoStreamCategory>

    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY WHERE isFav = 1 LIMIT :limit OFFSET :offset")
    suspend fun getSelectedVideoStreamCategoriesPaginationFav(limit: Int, offset: Int): List<VideoStreamCategory>

    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY WHERE categoryId= :reqCategoryId AND name LIKE :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getSelectedVideoStreamCategoriesPaginationWithSearch(
        reqCategoryId: String,
        searchQuery: String,
        limit: Int, offset: Int
    ): List<VideoStreamCategory>

    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY WHERE isFav = 1 AND name LIKE :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getSelectedVideoStreamCategoriesPaginationWithSearchFav(
        searchQuery: String,
        limit: Int, offset: Int
    ): List<VideoStreamCategory>

//    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY WHERE categoryId= :reqCategoryId ORDER BY name")
//    fun getSelectedVideoStreamCategoriesPagination(reqCategoryId: String): PagingSource<Int, VideoStreamCategory>
//
//    @Query("SELECT * FROM VIDEO_STREAM_CATEGORY WHERE categoryId= :reqCategoryId AND name LIKE :searchQuery ORDER BY name")
//    fun getSelectedVideoStreamCategoriesPaginationWithSearch(
//        reqCategoryId: String,
//        searchQuery: String
//    ): PagingSource<Int, VideoStreamCategory>

    @Query("DELETE FROM VIDEO_STREAM_CATEGORY")
    suspend fun deleteAllVideoStreamCategories()

}