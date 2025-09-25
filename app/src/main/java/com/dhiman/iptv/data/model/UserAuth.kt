package com.dhiman.iptv.data.model

import com.squareup.moshi.Json

data class UserAuth(
    @field:Json(name = "server_info")
    val serverInfo: ServerInfo = ServerInfo(),
    @field:Json(name = "user_info")
    val userInfo: UserInfo? = UserInfo()
)

data class ServerInfo(
    @field:Json(name = "https_port")
    val httpsPort: String? = "",
    @field:Json(name = "port")
    val port: String? = "",
    @field:Json(name = "rtmp_port")
    val rtmpPort: String? = "",
    @field:Json(name = "server_protocol")
    val serverProtocol: String? = "",
    @field:Json(name = "time_now")
    val timeNow: String? = "",
    @field:Json(name = "timestamp_now")
    val timestampNow: Int? = 0,
    @field:Json(name = "timezone")
    val timezone: String? = "",
    @field:Json(name = "url")
    val url: String? = ""
)

data class UserInfo(
    @field:Json(name = "active_cons")
    val activeCons: String? = "",
    @field:Json(name = "allowed_output_formats")
    val allowedOutputFormats: List<String> = listOf(),
    @field:Json(name = "auth")
    val auth: Int? = 0,
    @field:Json(name = "created_at")
    val createdAt: String? = "",
    @field:Json(name = "exp_date")
    val expDate: String? = "",
    @field:Json(name = "is_trial")
    val isTrial: String? = "",
    @field:Json(name = "max_connections")
    val maxConnections: String? = "",
    @field:Json(name = "message")
    val message: String? = "",
    @field:Json(name = "password")
    val password: String? = "",
    @field:Json(name = "status")
    val status: String? = "",
    @field:Json(name = "username")
    val username: String? = ""
)