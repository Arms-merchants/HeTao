package com.arm.hetao.ui.menuList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arm.hetao.bean.MenuListBean
import com.arm.hetao.request.Require
import com.arm.hetao.utils.TimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *    author : heyueyang
 *    time   : 2023/07/27
 *    desc   :
 *    version: 1.0
 */
class MenuListModel : ViewModel() {

    private val require by lazy {
        Require()
    }
    private val _data = MutableLiveData<MutableList<MenuListBean>?>()
    val data: LiveData<MutableList<MenuListBean>?> = _data
    var timeStr: MutableLiveData<String> = MutableLiveData(TimeUtils.getNowTimeString("yyyy-MM-dd"))

    fun getDataByDate(date: String? = null) {
        date?.let {
            timeStr.value = it
        }
        viewModelScope.launch(Dispatchers.IO) {
            timeStr.value?.let {
                _data.postValue(require.getMenuList(it))
            }
        }
    }

    fun getPreviousDayData() {
        timeStr.value = TimeUtils.getPreviousDay(timeStr.value)
        getDataByDate()
    }

    fun getNextDayData() {
        timeStr.value = TimeUtils.getNextDay(timeStr.value)
        getDataByDate()
    }

}