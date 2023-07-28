package com.arm.hetao.ui.videoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arm.hetao.bean.VideoListBean
import com.arm.hetao.request.Require
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
class VideoListModel : ViewModel() {
    private val require by lazy {
        Require()
    }

    private var page = 0
    private val _data = MutableLiveData<MutableList<VideoListBean>?>()
    val data: LiveData<MutableList<VideoListBean>?> = _data

    fun getVideoList(isRefresh: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isRefresh) {
                page = 0
            } else {
                page++
            }
            _data.postValue(require.getVideoList(page))
        }
    }


}