package com.dhiman.iptv.data.local.db.dao

import androidx.room.*
import com.dhiman.iptv.data.local.db.entity.M3UItemModel
import com.dhiman.iptv.data.local.db.entity.M3UPlaylistModel

@Dao
interface M3UPlaylistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllM3UPlaylistItems(dataList: List<M3UItemModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllM3UPlaylist(dataList: List<M3UPlaylistModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertM3UPlaylist(model: M3UPlaylistModel)

    @Update
    suspend fun updateM3UPlaylist(model: M3UItemModel)

    @Query("SELECT * FROM M3U_PLAYLIST")
    suspend fun getAllM3UPlaylist(): List<M3UPlaylistModel>

    @Query("SELECT * FROM M3U_PLAYLIST_ITEM WHERE category_id= :reqCategoryId")
    suspend fun getSelectedM3UPlaylistPagination(reqCategoryId: String): List<M3UItemModel>

    @Query("SELECT * FROM M3U_PLAYLIST_ITEM LIMIT :limit OFFSET :offset")
    suspend fun getSelectedM3UPlaylistNULLPagination(
        limit: Int,
        offset: Int
    ): List<M3UItemModel>

    @Query("SELECT * FROM M3U_PLAYLIST_ITEM WHERE isFavorite = 1")
    suspend fun getSelectedM3UPlaylistPaginationFav(): List<M3UItemModel>

    @Query("SELECT * FROM M3U_PLAYLIST_ITEM WHERE category_id= :reqCategoryId AND itemName LIKE :searchQuery")
    suspend fun getSelectedM3UPlaylistPaginationWithSearch(
        reqCategoryId: String,
        searchQuery: String
    ): List<M3UItemModel>

    @Query("SELECT * FROM M3U_PLAYLIST_ITEM WHERE itemName LIKE :searchQuery LIMIT :limit OFFSET :offset")
    suspend fun getSelectedM3UPlaylistPaginationNULLWithSearch(
        searchQuery: String,
        limit: Int,
        offset: Int
    ): List<M3UItemModel>

    @Query("SELECT * FROM M3U_PLAYLIST_ITEM WHERE isFavorite = 1 AND itemName LIKE :searchQuery")
    suspend fun getSelectedM3UPlaylistPaginationWithSearchFav(
        searchQuery: String
    ): List<M3UItemModel>

    @Query("DELETE FROM M3U_PLAYLIST")
    suspend fun deleteAllM3UPlaylist()

    @Query("DELETE FROM M3U_PLAYLIST_ITEM")
    suspend fun deleteAllM3UPlaylistItems()

}