package com.dhiman.iptv.data.local.db.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dhiman.iptv.data.local.db.entity.M3UItemModel
import com.dhiman.iptv.data.repository.RoomRepository
import javax.inject.Inject

class M3UPlaylistPagingSource @Inject constructor(
    private val roomRepository: RoomRepository,
    private val reqCategoryId: String,
    private val searchQuery: String
) :
    PagingSource<Int, M3UItemModel>() {

    private val limit = 15

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, M3UItemModel> {
        return try {
            val offset = params.key ?: 0
            val response = roomRepository.getSelectedM3UPlaylist(
                reqCategoryId,
                searchQuery,
                params.loadSize,
                offset * params.loadSize
            )

            LoadResult.Page(
                data = response,
                prevKey = if (offset == 0) null else offset - 1,
                nextKey = if (response.isEmpty()) null else offset + 1
            )

//            val offset = params.key ?: 0
//            val response = roomRepository.getSelectedM3UPlaylist(
//                reqCategoryId,
//                searchQuery,
//                params.loadSize,
//                offset * params.loadSize
//            )
//            val subResponse = if (offset + limit < response.size) response.subList(
//                offset,
//                offset + limit
//            ) else response.subList(offset, response.size)
//
//            Log.e("subResponse", ""+subResponse)
//
//            LoadResult.Page(
//                data = subResponse,
//                prevKey = null,
//                nextKey = if (offset + limit < response.size) offset + limit else null
//            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, M3UItemModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}