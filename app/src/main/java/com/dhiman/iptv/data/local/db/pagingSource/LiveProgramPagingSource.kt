package com.dhiman.iptv.data.local.db.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dhiman.iptv.data.local.db.entity.LiveStreamCategory
import com.dhiman.iptv.data.repository.RoomRepository
import javax.inject.Inject

class LiveProgramPagingSource @Inject constructor(
    private val roomRepository: RoomRepository,
    private val reqCategoryId: String,
    private val searchQuery: String
) :
    PagingSource<Int, LiveStreamCategory>() {

   // private val limit = 15

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, LiveStreamCategory> {
        return try {
            val offset = params.key ?: 0
            val response = roomRepository.getSelectedLiveStreamCategoriesPagination(
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

//            val subResponse = if (offset + limit < response.size) response.subList(
//                offset,
//                offset + limit
//            ) else response.subList(offset, response.size)
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

    override fun getRefreshKey(state: PagingState<Int, LiveStreamCategory>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}