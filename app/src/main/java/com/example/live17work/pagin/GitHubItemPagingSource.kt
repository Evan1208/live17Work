package com.example.live17work.pagin

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.example.live17work.api.GetUsersListData
import com.example.live17work.api.UtilApi
import java.io.IOException

class GitHubItemPagingSource(private val mApi: UtilApi
) : PagingSource<Int, GetUsersListData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetUsersListData> {

        return try {
            val key = params.key ?: 0
            val items = mApi.getGitHubUsers(20, key)
            LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = items.lastOrNull()?.id
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetUsersListData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}