package com.dhiman.iptv.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhiman.iptv.data.local.db.entity.SeriesCategoryModel

@Dao
interface SeriesCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSeriesCategories(dataList: List<SeriesCategoryModel>)

    @Query("SELECT * FROM SERIES_CATEGORY")
    suspend fun getAllSeriesCategories():List<SeriesCategoryModel>

    @Query("DELETE FROM SERIES_CATEGORY")
    suspend fun deleteAllSeriesCategories()

}