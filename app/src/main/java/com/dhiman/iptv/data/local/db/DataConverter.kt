package com.dhiman.iptv.data.local.db

import androidx.room.TypeConverter
import com.dhiman.iptv.data.local.db.entity.*
import com.dhiman.iptv.data.model.UserAuth
import com.dhiman.iptv.data.model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromLiveCategory(value: List<LiveCategoryModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<LiveCategoryModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLiveCategory(value: String): List<LiveCategoryModel> {
        val gson = Gson()
        val type = object : TypeToken<List<LiveCategoryModel>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromVideoCategory(value: List<VideoCategoryModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<VideoCategoryModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toVideoCategory(value: String): List<VideoCategoryModel> {
        val gson = Gson()
        val type = object : TypeToken<List<VideoCategoryModel>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSeriesCategory(value: List<SeriesCategoryModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SeriesCategoryModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSeriesCategory(value: String): List<SeriesCategoryModel> {
        val gson = Gson()
        val type = object : TypeToken<List<SeriesCategoryModel>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromLiveStreamCategory(value: List<LiveStreamCategory>): String {
        val gson = Gson()
        val type = object : TypeToken<List<LiveStreamCategory>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toLiveStreamCategory(value: String): List<LiveStreamCategory> {
        val gson = Gson()
        val type = object : TypeToken<List<LiveStreamCategory>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromVideoStreamCategory(value: List<VideoStreamCategory>): String {
        val gson = Gson()
        val type = object : TypeToken<List<VideoStreamCategory>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toVideoStreamCategory(value: String): List<VideoStreamCategory> {
        val gson = Gson()
        val type = object : TypeToken<List<VideoStreamCategory>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromSeriesStreamCategory(value: List<SeriesStreamCategory>): String {
        val gson = Gson()
        val type = object : TypeToken<List<SeriesStreamCategory>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toSeriesStreamCategory(value: String): List<SeriesStreamCategory> {
        val gson = Gson()
        val type = object : TypeToken<List<SeriesStreamCategory>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromBackDropPathCategory(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toBackDropPathCategory(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromUserAuth(value: UserAuth): String {
        val gson = Gson()
        val type = object : TypeToken<UserAuth>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toUserAuth(value: String): UserAuth {
        val gson = Gson()
        val type = object : TypeToken<UserAuth>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromUserModel(value: UserModel): String {
        val gson = Gson()
        val type = object : TypeToken<UserModel>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toUserModel(value: String): UserModel {
        val gson = Gson()
        val type = object : TypeToken<UserModel>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromM3UItem(value: List<M3UItemModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<M3UItemModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toM3UItem(value: String): List<M3UItemModel> {
        val gson = Gson()
        val type = object : TypeToken<List<M3UItemModel>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromCommonCategory(value: List<CommonCategoryModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<CommonCategoryModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toCommonCategory(value: String): List<CommonCategoryModel> {
        val gson = Gson()
        val type = object : TypeToken<List<CommonCategoryModel>>() {}.type
        return gson.fromJson(value, type)
    }

}