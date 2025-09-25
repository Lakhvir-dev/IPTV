package com.dhiman.iptv.data.local.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "SERIES_STREAM_CATEGORY")
data class SeriesStreamCategory(
    @field:Json(name = "backdrop_path")
    val backdropPath: List<String> = listOf(),
    @field:Json(name = "cast")
    val cast: String = "",
    @field:Json(name = "category_id")
    val categoryId: String = "",
    @field:Json(name = "cover")
    val cover: String = "",
    @field:Json(name = "director")
    val director: String = "",
    @field:Json(name = "episode_run_time")
    val episodeRunTime: String = "",
    @field:Json(name = "genre")
    val genre: String = "",
    @field:Json(name = "last_modified")
    val lastModified: String = "",
    @field:Json(name = "name")
    val name: String = "",
    @PrimaryKey
    @field:Json(name = "num")
    val num: String = "",
    @field:Json(name = "plot")
    val plot: String = "",
    @field:Json(name = "rating")
    val rating: String = "",
    @field:Json(name = "rating_5based")
    val rating5based: String = "",
    @field:Json(name = "releaseDate")
    val releaseDate: String = "",
    @field:Json(name = "series_id")
    val seriesId: String = "",
    @field:Json(name = "youtube_trailer")
    val youtubeTrailer: String = "",
    var isFav: Boolean = false
)