package com.arm.hetao.ui.playList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arm.hetao.request.Require
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *    author : heyueyang
 *    time   : 2023/07/25
 *    desc   :
 *    version: 1.0
 */
class PlayListViewModel : ViewModel() {
    private val _data = MutableLiveData<ArrayList<Pair<String, String>>>()
    val data: LiveData<ArrayList<Pair<String, String>>> = _data

    private val request by lazy {
        Require()
    }

    fun getData() {
        Log.e("TAG","getData")
        viewModelScope.launch(Dispatchers.IO) {
            Log.e("TAG","viewModelScope")
            request.getChannel(data = _data)
        }
    }

}