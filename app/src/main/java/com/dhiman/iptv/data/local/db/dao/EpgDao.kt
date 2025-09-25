package com.dhiman.iptv.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dhiman.iptv.data.local.db.entity.EpgModel

@Dao
interface EpgDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEpg(dataList: List<EpgModel>)

    @Query("SELECT * FROM EPG WHERE channel= :reqEpgChannelId")
    suspend fun getSelectedEpg(reqEpgChannelId: String): List<EpgModel>

    @Query("DELETE FROM EPG")
    suspend fun deleteAllEpg()

}