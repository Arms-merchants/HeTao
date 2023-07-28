package com.arm.hetao.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arm.hetao.request.Require
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
class MainViewModel : ViewModel() {

    private val required: Require by lazy {
        Require()
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            required.getUserInfo()
        }
    }


}