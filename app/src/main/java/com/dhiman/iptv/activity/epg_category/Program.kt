package com.dhiman.iptv.activity.epg_category

data class Program(
    val title: String,
    val startMinutes: Int, // in minutes from day start
    val endMinutes: Int
)