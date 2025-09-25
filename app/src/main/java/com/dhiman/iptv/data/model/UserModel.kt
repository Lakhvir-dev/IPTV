package com.dhiman.iptv.data.model

import java.io.Serializable

class UserModel : Serializable {
    var id: Int = 0
    var mainUrl: String = ""
    var userName: String = ""
    var password: String = ""
    var playListName: String = ""
    var playListType: String = ""
    var url: String? = null
}