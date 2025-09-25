package com.dhiman.iptv.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhiman.iptv.data.local.db.entity.CommonCategoryModel

@Dao
interface CommonCategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCommonCategories(dataList: List<CommonCategoryModel>)

    @Query("SELECT * FROM COMMON_CATEGORY")
    suspend fun getAllCommonCategories(): List<CommonCategoryModel>

    @Query("DELETE FROM COMMON_CATEGORY")
    suspend fun deleteAllCommonCategories()

}