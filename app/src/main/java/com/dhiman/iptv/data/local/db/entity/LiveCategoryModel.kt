package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "LIVE_CATEGORY")
data class LiveCategoryModel(
    @PrimaryKey
    @field:Json(name = "category_id")
    var categoryId: String = "",
    @field:Json(name = "category_name")
    var categoryName: String = "",
    @field:Json(name = "parent_id")
    var parentId: String = ""
)
