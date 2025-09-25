package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "VIDEO_CATEGORY")
data class VideoCategoryModel(
    @PrimaryKey
    @field:Json(name = "category_id")
    val categoryId: String = "",
    @field:Json(name = "category_name")
    val categoryName: String = "",
    @field:Json(name = "parent_id")
    val parentId: String = ""
)
