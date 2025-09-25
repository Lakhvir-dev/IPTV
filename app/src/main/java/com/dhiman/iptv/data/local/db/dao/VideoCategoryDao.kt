package com.dhiman.iptv.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhiman.iptv.data.local.db.entity.VideoCategoryModel

@Dao
interface VideoCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllVideoCategories(dataList: List<VideoCategoryModel>)

    @Query("SELECT * FROM VIDEO_CATEGORY")
    suspend fun getAllVideoCategories(): List<VideoCategoryModel>

    @Query("DELETE FROM VIDEO_CATEGORY")
    suspend fun deleteAllVideoCategories()

}