package com.dhiman.iptv.util

import com.dhiman.iptv.data.local.db.entity.CommonCategoryModel
import com.dhiman.iptv.data.local.db.entity.M3UItemModel
import com.dhiman.iptv.data.local.db.entity.M3UPlaylistModel
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

object ParseFile {

    private const val EXT_M3U = "#EXTM3U"
    private const val EXT_INF = "#EXTINF:"
    private const val EXT_PLAYLIST_NAME = "#PLAYLIST"
    private const val EXT_LOGO = "tvg-logo"
    private const val EXT_GROUP = "group-title"
    private const val EXT_URL = "http://"

    fun checkFileContainUrl(url: String): Boolean {
        val file = File(url)
        val fileInputStream = FileInputStream(file)
        val fileString = convertStreamToString(fileInputStream)
        return fileString.contains("http://") || fileString.contains("https://")
    }

    private fun convertStreamToString(inputStream: InputStream): String {
        return try {
            Scanner(inputStream).useDelimiter("\\A").next()
        } catch (e: NoSuchElementException) {
            ""
        }
    }

    fun fetchAndParseFile(url: String): M3UPlaylistModel {
        val file = File(url)
        val fileInputStream = FileInputStream(file.path)
        return parseFile(fileInputStream)
    }

    @Throws(FileNotFoundException::class)
    private fun parseFile(inputStream: InputStream): M3UPlaylistModel {
        val m3uPlayListModel = M3UPlaylistModel()
        val playlistItems = mutableListOf<M3UItemModel>()
        val categoryModelList = mutableListOf<CommonCategoryModel>()
        val hashMap = HashMap<String, Int>()
        val stream = convertStreamToString(inputStream)
        val linesArray =
            stream.split(EXT_INF.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        var count = 0
        for (i in linesArray.indices) {
            val currLine = linesArray[i]
            if (currLine.contains(EXT_M3U)) {
                //header of file
                if (currLine.contains(EXT_PLAYLIST_NAME)) {
                    val fileParams =
                        currLine.substring(EXT_M3U.length, currLine.indexOf(EXT_PLAYLIST_NAME))
                    val playListName =
                        currLine.substring(currLine.indexOf(EXT_PLAYLIST_NAME) + EXT_PLAYLIST_NAME.length)
                            .replace(":", "")
                    m3uPlayListModel.playlistName = playListName
                    m3uPlayListModel.playlistParams = fileParams
                } else {
                    m3uPlayListModel.playlistName = "No Name Playlist"
                    m3uPlayListModel.playlistParams = "No Params"
                }
            } else {
                val playlistItem = M3UItemModel()
                val categoryModel = CommonCategoryModel()
                val dataArray =
                    currLine.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (dataArray.isEmpty()) {
                    continue
                }
                if (dataArray[0].contains(EXT_LOGO)) {
                    val duration =
                        dataArray[0].substring(0, dataArray[0].indexOf(EXT_LOGO)).replace(":", "")
                            .replace("\n", "")
                    val icon =
                        dataArray[0].substring(dataArray[0].indexOf(EXT_LOGO) + EXT_LOGO.length)
                            .replace("=", "")
                            .replace("\"", "").replace("\n", "")
                    val categoryName =
                        dataArray[0].substring(dataArray[0].indexOf(EXT_GROUP) + EXT_GROUP.length)
                            .replace("=", "")
                            .replace("\"", "").replace("\n", "")
                    if (categoryName.isNotBlank()) {
                        if (hashMap.containsKey(categoryName)) {
                            playlistItem.category_id = hashMap.getValue(categoryName).toString()
                        } else {
                            count += 1
                            hashMap[categoryName] = count
                            categoryModel.category_id = count.toString()
                            categoryModel.category_name = categoryName
                            categoryModelList.add(categoryModel)
                            playlistItem.category_id = count.toString()
                        }
                    }

                    playlistItem.itemDuration = duration
                    playlistItem.itemIcon = icon
                } else {
                    val duration = dataArray[0].replace(":", "").replace("\n", "")
                    playlistItem.itemDuration = duration
                    playlistItem.itemIcon = ""
                }
                try {
                    if (dataArray.size > 2) {
                        for (index in 2 until dataArray.size)
                            dataArray[1] = dataArray[1] + "," + dataArray[index]
                    }
                    val url =
                        dataArray[1].substring(dataArray[1].indexOf(EXT_URL)).replace("\n", "")
                            .replace("\r", "")
                    val name =
                        dataArray[1].substring(0, dataArray[1].indexOf(EXT_URL)).replace("\n", "")
                    playlistItem.itemName = name
                    playlistItem.itemUrl = url
                    playlistItem.id = i
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                playlistItems.add(playlistItem)
            }
        }
        m3uPlayListModel.playlistItems = playlistItems
        m3uPlayListModel.commonCategoryList = categoryModelList

        return m3uPlayListModel
    }

}