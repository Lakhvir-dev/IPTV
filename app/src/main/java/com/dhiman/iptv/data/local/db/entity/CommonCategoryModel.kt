package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "COMMON_CATEGORY")
data class CommonCategoryModel(
    @PrimaryKey
    var category_id: String = "",
    var category_name: String = ""
)
