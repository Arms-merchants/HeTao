package com.arm.hetao.ui.videoList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arm.hetao.bean.VideoListBean
import com.arm.hetao.request.Require

/**
 *    author : heyueyang
 *    time   : 2023/08/04
 *    desc   :
 *    version: 1.0
 */
class VideoPageSource(val require: Require) : PagingSource<Int, VideoListBean>() {

    override fun getRefreshKey(state: PagingState<Int, VideoListBean>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoListBean> {
        return try {
            val page = params.key ?: 0
            val videoList = require.getVideoList(page)
            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (videoList?.isNotEmpty() == true) page + 1 else null
            LoadResult.Page(videoList ?: listOf(), prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}