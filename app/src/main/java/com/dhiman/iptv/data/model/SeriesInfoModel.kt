package com.dhiman.iptv.data.model

import com.google.gson.annotations.SerializedName

data class SeriesInfoModel(
    var episodes: ArrayList<Episodes> = ArrayList(),
    var info: SeriesInfo = SeriesInfo(),
    var seasons: List<Season> = listOf()
) {
    data class Episodes(
        @SerializedName("added")
        val added: String = "",
        @SerializedName("container_extension")
        val containerExtension: String = "",
        @SerializedName("custom_sid")
        val customSid: String = "",
        @SerializedName("direct_source")
        val directSource: String = "",
        @SerializedName("episode_num")
        val episodeNum: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("info")
        val info: Info = Info(),
        @SerializedName("season")
        val season: String = "",
        @SerializedName("title")
        val title: String = ""
    )

    data class Info(
        @SerializedName("bitrate")
        val bitrate: String = "",
        @SerializedName("duration")
        val duration: String = "",
        @SerializedName("duration_secs")
        val durationSecs: String = "",
        @SerializedName("movie_image")
        val movieImage: String = "",
        @SerializedName("plot")
        val plot: String = "",
        @SerializedName("rating")
        val rating: String = "",
        @SerializedName("releasedate")
        val releasedate: String = "",
        @SerializedName("season")
        val season: String = "",
        @SerializedName("tmdb_id")
        val tmdbId: String = "",
    )

    data class SeriesInfo(
        @SerializedName("backdrop_path")
        val backdropPath: List<String> = listOf(),
        @SerializedName("cast")
        val cast: String = "",
        @SerializedName("category_id")
        val categoryId: String = "",
        @SerializedName("cover")
        val cover: String = "",
        @SerializedName("director")
        val director: String = "",
        @SerializedName("episode_run_time")
        val episodeRunTime: String = "",
        @SerializedName("genre")
        val genre: String = "",
        @SerializedName("last_modified")
        val lastModified: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("plot")
        val plot: String = "",
        @SerializedName("rating")
        val rating: String = "",
        @SerializedName("rating_5based")
        val rating5based: String = "",
        @SerializedName("releaseDate")
        val releaseDate: String = "",
        @SerializedName("youtube_trailer")
        val youtubeTrailer: String = ""
    )

    data class Season(
        @SerializedName("air_date")
        val airDate: String = "",
        @SerializedName("cover")
        val cover: String = "",
        @SerializedName("cover_big")
        val coverBig: String = "",
        @SerializedName("episode_count")
        val episodeCount: String = "",
        @SerializedName("id")
        val id: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("overview")
        val overview: String = "",
        @SerializedName("season_number")
        val seasonNumber: String = ""
    )

}