package com.dhiman.iptv.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhiman.iptv.data.local.db.entity.LiveCategoryModel

@Dao
interface LiveCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLiveCategories(dataList: List<LiveCategoryModel>)

    @Query("SELECT * FROM LIVE_CATEGORY")
    suspend fun getAllLiveCategories(): List<LiveCategoryModel>

    @Query("DELETE FROM LIVE_CATEGORY")
    suspend fun deleteAllLiveCategories()

}