package com.arm.hetao.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arm.hetao.bean.UserInfoBean
import com.arm.hetao.request.Require
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
class SettingModel : ViewModel() {

    private val required: Require by lazy {
        Require()
    }

    private val _data = MutableLiveData<UserInfoBean?>()
    val data: LiveData<UserInfoBean?> = _data

    fun login(phone: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfoBean = required.getUserInfo4Http(phone, password)
            _data.postValue(userInfoBean)
        }
    }
}