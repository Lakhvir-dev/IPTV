package com.dhiman.iptv.data.model

import com.squareup.moshi.Json

data class MovieInfoModel(
    @field:Json(name = "info")
    val info: Info = Info(),
    @field:Json(name = "movie_data")
    val movieData: MovieData = MovieData()
)

data class Info(
    @field:Json(name = "actors")
    val actors: String = "",
    @field:Json(name = "age")
    val age: String = "",
    @field:Json(name = "audio")
    val audio: Any = Audio(),
    @field:Json(name = "backdrop_path")
    val backdropPath: List<String> = listOf(),
    @field:Json(name = "bitrate")
    val bitrate: String = "",
    @field:Json(name = "cast")
    val cast: String = "",
    @field:Json(name = "country")
    val country: String = "",
    @field:Json(name = "cover_big")
    val coverBig: String = "",
    @field:Json(name = "description")
    val description: String = "",
    @field:Json(name = "director")
    val director: String = "",
    @field:Json(name = "duration")
    val duration: String = "",
    @field:Json(name = "duration_secs")
    val durationSecs: String = "",
    @field:Json(name = "episode_run_time")
    val episodeRunTime: String = "",
    @field:Json(name = "genre")
    val genre: String = "",
    @field:Json(name = "kinopoisk_url")
    val kinopoiskUrl: String = "",
    @field:Json(name = "movie_image")
    val movieImage: String = "",
    @field:Json(name = "mpaa_rating")
    val mpaaRating: String = "",
    @field:Json(name = "name")
    val name: String = "",
    @field:Json(name = "o_name")
    val oName: String = "",
    @field:Json(name = "plot")
    val plot: String = "",
    @field:Json(name = "rating")
    val rating: String = "",
    @field:Json(name = "rating_count_kinopoisk")
    val ratingCountKinopoisk: String = "",
    @field:Json(name = "releasedate")
    val releasedate: String = "",
    @field:Json(name = "tmdb_id")
    val tmdbId: String = "",
    @field:Json(name = "video")
    val video: Any = Video(),
    @field:Json(name = "youtube_trailer")
    val youtubeTrailer: String = ""
)

data class Audio(
    @field:Json(name = "avg_frame_rate")
    val avgFrameRate: String = "",
    @field:Json(name = "bits_per_sample")
    val bitsPerSample: String = "",
    @field:Json(name = "channel_layout")
    val channelLayout: String = "",
    @field:Json(name = "channels")
    val channels: String = "",
    @field:Json(name = "codec_long_name")
    val codecLongName: String = "",
    @field:Json(name = "codec_name")
    val codecName: String = "",
    @field:Json(name = "codec_tag")
    val codecTag: String = "",
    @field:Json(name = "codec_tag_string")
    val codecTagString: String = "",
    @field:Json(name = "codec_time_base")
    val codecTimeBase: String = "",
    @field:Json(name = "codec_type")
    val codecType: String = "",
    @field:Json(name = "disposition")
    val disposition: Disposition = Disposition(),
    @field:Json(name = "index")
    val index: String = "",
    @field:Json(name = "profile")
    val profile: String = "",
    @field:Json(name = "r_frame_rate")
    val rFrameRate: String = "",
    @field:Json(name = "sample_fmt")
    val sampleFmt: String = "",
    @field:Json(name = "sample_rate")
    val sampleRate: String = "",
    @field:Json(name = "start_pts")
    val startPts: String = "",
    @field:Json(name = "start_time")
    val startTime: String = "",
    @field:Json(name = "tags")
    val tags: Tags = Tags(),
    @field:Json(name = "time_base")
    val timeBase: String = ""
)

data class Disposition(
    @field:Json(name = "attached_pic")
    val attachedPic: String = "",
    @field:Json(name = "clean_effects")
    val cleanEffects: String = "",
    @field:Json(name = "comment")
    val comment: String = "",
    @field:Json(name = "default")
    val default: String = "",
    @field:Json(name = "dub")
    val dub: String = "",
    @field:Json(name = "forced")
    val forced: String = "",
    @field:Json(name = "hearing_impaired")
    val hearingImpaired: String = "",
    @field:Json(name = "karaoke")
    val karaoke: String = "",
    @field:Json(name = "lyrics")
    val lyrics: String = "",
    @field:Json(name = "original")
    val original: String = "",
    @field:Json(name = "timed_thumbnails")
    val timedThumbnails: String = "",
    @field:Json(name = "visual_impaired")
    val visualImpaired: String = ""
)

data class Tags(
    @field:Json(name = "BPS-eng")
    val bPSEng: String = "",
    @field:Json(name = "DURATION-eng")
    val dURATIONEng: String = "",
    @field:Json(name = "NUMBER_OF_BYTES-eng")
    val nUMBEROFBYTESEng: String = "",
    @field:Json(name = "NUMBER_OF_FRAMES-eng")
    val nUMBEROFFRAMESEng: String = "",
    @field:Json(name = "_STATISTICS_TAGS-eng")
    val sTATISTICSTAGSEng: String = "",
    @field:Json(name = "_STATISTICS_WRITING_APP-eng")
    val sTATISTICSWRITINGAPPEng: String = "",
    @field:Json(name = "_STATISTICS_WRITING_DATE_UTC-eng")
    val sTATISTICSWRITINGDATEUTCEng: String = ""
)

data class Video(
    @field:Json(name = "avg_frame_rate")
    val avgFrameRate: String = "",
    @field:Json(name = "bits_per_raw_sample")
    val bitsPerRawSample: String = "",
    @field:Json(name = "chroma_location")
    val chromaLocation: String = "",
    @field:Json(name = "codec_long_name")
    val codecLongName: String = "",
    @field:Json(name = "codec_name")
    val codecName: String = "",
    @field:Json(name = "codec_tag")
    val codecTag: String = "",
    @field:Json(name = "codec_tag_string")
    val codecTagString: String = "",
    @field:Json(name = "codec_time_base")
    val codecTimeBase: String = "",
    @field:Json(name = "codec_type")
    val codecType: String = "",
    @field:Json(name = "coded_height")
    val codedHeight: String = "",
    @field:Json(name = "coded_width")
    val codedWidth: String = "",
    @field:Json(name = "color_primaries")
    val colorPrimaries: String = "",
    @field:Json(name = "color_range")
    val colorRange: String = "",
    @field:Json(name = "color_space")
    val colorSpace: String = "",
    @field:Json(name = "color_transfer")
    val colorTransfer: String = "",
    @field:Json(name = "display_aspect_ratio")
    val displayAspectRatio: String = "",
    @field:Json(name = "disposition")
    val disposition: Disposition = Disposition(),
    @field:Json(name = "field_order")
    val fieldOrder: String = "",
    @field:Json(name = "has_b_frames")
    val hasBFrames: String = "",
    @field:Json(name = "height")
    val height: String = "",
    @field:Json(name = "index")
    val index: String = "",
    @field:Json(name = "is_avc")
    val isAvc: String = "",
    @field:Json(name = "level")
    val level: String = "",
    @field:Json(name = "nal_length_size")
    val nalLengthSize: String = "",
    @field:Json(name = "pix_fmt")
    val pixFmt: String = "",
    @field:Json(name = "profile")
    val profile: String = "",
    @field:Json(name = "r_frame_rate")
    val rFrameRate: String = "",
    @field:Json(name = "refs")
    val refs: String = "",
    @field:Json(name = "sample_aspect_ratio")
    val sampleAspectRatio: String = "",
    @field:Json(name = "start_pts")
    val startPts: String = "",
    @field:Json(name = "start_time")
    val startTime: String = "",
    @field:Json(name = "tags")
    val tags: Tags = Tags(),
    @field:Json(name = "time_base")
    val timeBase: String = "",
    @field:Json(name = "width")
    val width: String = ""
)

data class MovieData(
    @field:Json(name = "added")
    val added: String = "",
    @field:Json(name = "category_id")
    val categoryId: String = "",
    @field:Json(name = "container_extension")
    val containerExtension: String = "",
    @field:Json(name = "custom_sid")
    val customSid: String = "",
    @field:Json(name = "direct_source")
    val directSource: String = "",
    @field:Json(name = "name")
    val name: String = "",
    @field:Json(name = "stream_id")
    val streamId: String = ""
)
