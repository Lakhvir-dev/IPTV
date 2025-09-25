package com.dhiman.iptv.data.local.db.dao

import androidx.room.*
import com.dhiman.iptv.data.local.db.entity.SeriesStreamCategory

@Dao
interface SeriesStreamCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSeriesStreamCategories(dataList: List<SeriesStreamCategory>)

    @Update
    suspend fun updateSeriesStreamCategory(model: SeriesStreamCategory)

    @Query("SELECT * FROM SERIES_STREAM_CATEGORY")
    suspend fun getAllSeriesStreamCategories(): List<SeriesStreamCategory>

    @Query("SELECT * FROM SERIES_STREAM_CATEGORY WHERE categoryId= :reqCategoryId LIMIT :limit OFFSET :offset")
    suspend fun getSelectedSeriesStreamCategoriesPagination(
        reqCategoryId: String,
        limit: Int,
        offset: Int
    ): List<SeriesStreamCategory>

    @Query("SELECT * FROM SERIES_STREAM_CATEGORY WHERE isFav = 1 LIMIT :limit OFFSET :offset")
    suspend fun getSelectedSeriesStreamCategoriesPaginationFav(
        limit: Int,
        offset: Int
    ): List<SeriesStreamCategory>

    @Query("SELECT * FROM SERIES_STREAM_CATEGORY WHERE categoryId= :reqCategoryId AND name LIKE :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getSelectedSeriesStreamCategoriesPaginationWithSearch(
        reqCategoryId: String,
        searchQuery: String,
        limit: Int, offset: Int
    ): List<SeriesStreamCategory>

    @Query("SELECT * FROM SERIES_STREAM_CATEGORY WHERE isFav = 1 AND name LIKE :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getSelectedSeriesStreamCategoriesPaginationWithSearchFav(
        searchQuery: String,
        limit: Int, offset: Int
    ): List<SeriesStreamCategory>

//    @Query("SELECT * FROM SERIES_STREAM_CATEGORY WHERE categoryId= :reqCategoryId ORDER BY name")
//    fun getSelectedSeriesStreamCategoriesPagination(reqCategoryId: String): PagingSource<Int, SeriesStreamCategory>
//
//    @Query("SELECT * FROM SERIES_STREAM_CATEGORY WHERE categoryId= :reqCategoryId AND name LIKE :searchQuery ORDER BY name")
//    fun getSelectedSeriesStreamCategoriesPaginationWithSearch(
//        reqCategoryId: String,
//        searchQuery: String
//    ): PagingSource<Int, SeriesStreamCategory>

    @Query("DELETE FROM SERIES_STREAM_CATEGORY")
    suspend fun deleteAllSeriesStreamCategories()

}