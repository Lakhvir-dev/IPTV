package com.dhiman.iptv.data.model.catchUp

import com.squareup.moshi.Json

data class CatchUpModel(
    @field:Json(name = "epg_listings")
    val epgListings: List<EpgListings>? = null
)

data class EpgListings(
    @field:Json(name = "channel_id")
    val channelId: String? = null,
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "end")
    val end: String? = null,
    @field:Json(name = "epg_id")
    val epgId: String? = null,
    @field:Json(name = "has_archive")
    val hasArchive: Int? = null,
    @field:Json(name = "id")
    val id: String? = null,
    @field:Json(name = "lang")
    val lang: String? = null,
    @field:Json(name = "now_playing")
    val nowPlaying: Int? = null,
    @field:Json(name = "start")
    val start: String? = null,
    @field:Json(name = "start_timestamp")
    val startTimestamp: String? = null,
    @field:Json(name = "stop_timestamp")
    val stopTimestamp: String? = null,
    @field:Json(name = "title")
    val title: String? = null
)