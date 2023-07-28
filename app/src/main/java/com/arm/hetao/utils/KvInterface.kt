package com.arm.hetao.utils

import android.content.Context

/**
 *    author : heyueyang
 *    time   : 2023/01/16
 *    desc   :
 *    version: 1.0
 */
interface KvInterface {

    fun init(context: Context)

    fun getBoolean(key: String, defaultValue: Boolean? = null): Boolean

    fun putBoolean(key: String, value: Boolean)

    fun getInt(key: String, defaultValueL: Int? = null): Int?

    fun putInt(key: String, value: Int)

    fun getString(key: String, defaultValueL: String? = null): String?

    fun putString(key: String, value: String)

}