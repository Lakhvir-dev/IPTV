package com.dhiman.iptv.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EPG")
data class EpgModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var channel: String = "",
    var desc: String = "",
    var start: String = "",
    var stop: String = "",
    var title: String = "",
    var streamId: String = "",
    var isFav: Boolean = false
)
