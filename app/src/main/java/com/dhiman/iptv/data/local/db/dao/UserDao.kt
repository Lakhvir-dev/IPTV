package com.dhiman.iptv.data.local.db.dao

import androidx.room.*
import com.dhiman.iptv.data.local.db.entity.UserDataModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(model: UserDataModel)

    @Query("SELECT * FROM USER_DATA_MODEL")
    suspend fun getAllUsers(): List<UserDataModel>

    @Update
    suspend fun updateUser(model: UserDataModel)

    @Delete
    suspend fun deleteUser(model: UserDataModel)

}