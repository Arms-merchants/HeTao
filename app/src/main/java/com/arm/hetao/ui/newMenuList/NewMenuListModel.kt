package com.arm.hetao.ui.newMenuList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arm.hetao.bean.MenuListAdapterBean
import com.arm.hetao.request.Require
import com.arm.hetao.utils.TimeUtils
import kotlinx.coroutines.launch

/**
 *    author : heyueyang
 *    time   : 2023/07/28
 *    desc   :
 *    version: 1.0
 */
class NewMenuListModel : ViewModel() {

    private val required by lazy {
        Require()
    }
    private val _data = MutableLiveData<MutableList<MenuListAdapterBean>?>()
    val data: LiveData<MutableList<MenuListAdapterBean>?> = _data
    private var currentDate = TimeUtils.getNowTimeString("yyyy-MM-dd")

    fun getData(isCurrentDate: Boolean = false) {
        if (isCurrentDate) {
            currentDate = TimeUtils.getNowTimeString("yyyy-MM-dd")
        } else {
            currentDate = TimeUtils.getPreviousDay(currentDate, -20) ?: ""
        }
        viewModelScope.launch {
            _data.postValue(required.getMenuList2(currentDate))
        }
    }

    fun isCurrentDay():Boolean{
        return currentDate == TimeUtils.getNowTimeString("yyyy-MM-dd")
    }


}