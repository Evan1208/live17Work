package com.example.live17work.myViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.live17work.api.GetUsersListData
import com.example.live17work.api.UtilApi
import com.example.live17work.pagin.GitHubItemPagingSource
import kotlinx.coroutines.flow.Flow

class GithubListModel : ViewModel() {


    fun checkUserName(pContent: String): String? {
        val iContent = pContent.trim()
        return if( iContent.isEmpty()) {
            null
        } else {
            iContent
        }
    }

    val mFlow : Flow<PagingData<GetUsersListData>> = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        GitHubItemPagingSource(UtilApi.create())
    }.flow.cachedIn(viewModelScope)



}