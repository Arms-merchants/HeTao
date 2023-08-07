package com.arm.hetao.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arm.hetao.bean.UserInfoBean
import com.arm.hetao.bean.VersionBean
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

    val versionData: MutableLiveData<VersionBean?> = MutableLiveData()
    val userInfo: MutableLiveData<UserInfoBean?> = MutableLiveData()

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            userInfo.postValue(required.getUserInfo())
        }
    }

    fun getNewVersion() {
        viewModelScope.launch(Dispatchers.IO) {
            val versionBean = required.getNewVersion()
            versionData.postValue(versionBean)
        }
    }


}