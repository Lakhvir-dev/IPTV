package com.dhiman.iptv.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dhiman.iptv.data.local.db.dao.*
import com.dhiman.iptv.data.local.db.entity.*

@Database(
    entities = [LiveCategoryModel::class, VideoCategoryModel::class,
        SeriesCategoryModel::class, LiveStreamCategory::class,
        VideoStreamCategory::class, SeriesStreamCategory::class,
        EpgModel::class, UserDataModel::class,
        CommonCategoryModel::class, M3UPlaylistModel::class,
        M3UItemModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun liveCategoryDao(): LiveCategoryDao
    abstract fun videoCategoryDao(): VideoCategoryDao
    abstract fun seriesCategoryDao(): SeriesCategoryDao
    abstract fun liveStreamCategoryDao(): LiveStreamCategoryDao
    abstract fun videoStreamCategoryDao(): VideoStreamCategoryDao
    abstract fun seriesStreamCategoryDao(): SeriesStreamCategoryDao
    abstract fun epgDao(): EpgDao
    abstract fun userDao(): UserDao
    abstract fun commonCategoryDao(): CommonCategoryDao
    abstract fun m3uPlaylistDao(): M3UPlaylistDao
}