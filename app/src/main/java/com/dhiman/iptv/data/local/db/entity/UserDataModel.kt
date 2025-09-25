package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.model.UserModel

@Entity(tableName = "USER_DATA_MODEL")
data class UserDataModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var userModel: UserModel,
    var userAuth: UserAuth = UserAuth()
)
