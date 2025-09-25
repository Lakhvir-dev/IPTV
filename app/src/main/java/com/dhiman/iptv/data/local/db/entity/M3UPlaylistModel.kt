package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "M3U_PLAYLIST")
data class M3UPlaylistModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var playlistName: String? = null,
    var playlistParams: String? = null,
    var playlistItems: List<M3UItemModel>? = null,
    var commonCategoryList: List<CommonCategoryModel>? = null
)