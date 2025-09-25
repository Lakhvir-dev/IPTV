package com.dhiman.iptv.data.local.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "LIVE_STREAM_CATEGORY")
data class LiveStreamCategory(
    @field:Json(name = "added")
    val added: String = "",
    @field:Json(name = "category_id")
    val categoryId: String? = null,
    @field:Json(name = "custom_sid")
    val customSid: String? = null,
    @field:Json(name = "direct_source")
    val directSource: String = "",
    @field:Json(name = "epg_channel_id")
    val epgChannelId: String? = null,
    @field:Json(name = "name")
    val name: String = "",
    @PrimaryKey
    @field:Json(name = "num")
    val num: String = "",
    @field:Json(name = "stream_icon")
    val streamIcon: String = "",
    @field:Json(name = "stream_id")
    val streamId: String = "",
    @field:Json(name = "stream_type")
    val streamType: String = "",
    @field:Json(name = "tv_archive")
    val tvArchive: String = "",
    @field:Json(name = "tv_archive_duration")
    val tvArchiveDuration: String = "",
    var isFav: Boolean = false
)