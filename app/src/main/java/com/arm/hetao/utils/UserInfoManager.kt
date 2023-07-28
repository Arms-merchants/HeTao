package com.arm.hetao.utils

import android.util.Log
import com.arm.hetao.bean.UserInfoBean

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
object UserInfoManager {

    fun getUserInfoBean(): UserInfoBean? {
        val json = MMKVKv.instance.getString("UserInfoBean") ?: return null
        Log.e("TAG", "getUserInfoBean$json")
        return fromJsonToAnyByMoshi<UserInfoBean>(json)
    }

    fun saveUserInfoBean(userInfoBean: UserInfoBean) {
        val json = toJsonByMoshi(userInfoBean)
        MMKVKv.instance.putString("UserInfoBean", json)
    }
}