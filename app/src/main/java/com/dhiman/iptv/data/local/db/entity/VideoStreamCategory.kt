package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "VIDEO_STREAM_CATEGORY")
data class VideoStreamCategory(
    @field:Json(name = "added")
    val added: String? = null,
    @field:Json(name = "category_id")
    val categoryId: String? = null,
    @field:Json(name = "container_extension")
    val containerExtension: String? = null,
    @field:Json(name = "custom_sid")
    val customSid: String? = null,
    @field:Json(name = "direct_source")
    val directSource: String? = null,
    @field:Json(name = "name")
    val name: String? = null,
    @PrimaryKey
    @field:Json(name = "num")
    val num: String = "",
    @field:Json(name = "rating")
    val rating: String? = null,
    @field:Json(name = "rating_5based")
    val rating5based: String? = null,
    @field:Json(name = "stream_icon")
    val streamIcon: String? = null,
    @field:Json(name = "stream_id")
    val streamId: String? = null,
    @field:Json(name = "stream_type")
    val streamType: String? = null,
    var isFav: Boolean = false
)