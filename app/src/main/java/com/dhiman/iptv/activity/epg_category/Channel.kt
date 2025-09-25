package com.dhiman.iptv.activity.epg_category

data class Channel(
    val name: String,
    val logoRes: Int,
    val programs: List<Program>
)