package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "M3U_PLAYLIST_ITEM")
data class M3UItemModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var category_id: String? = null,
    var itemDuration: String? = null,
    var itemName: String? = null,
    var itemUrl: String = "",
    var itemIcon: String? = null,
    var isFavorite: Boolean = false,
)
