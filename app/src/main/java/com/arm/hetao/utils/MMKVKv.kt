package com.arm.hetao.utils

import android.content.Context
import com.tencent.mmkv.MMKV

/**
 *    author : heyueyang
 *    time   : 2023/01/16
 *    desc   :
 *    version: 1.0
 */
class MMKVKv private constructor() : KvInterface {

    private lateinit var kv: MMKV

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MMKVKv()
        }
    }

    override fun init(context: Context) {
        MMKV.initialize(context)
        kv = MMKV.defaultMMKV()
    }

    override fun getBoolean(key: String, defaultValue: Boolean?): Boolean {
        return if (defaultValue != null) {
            kv.decodeBool(key, defaultValue)
        } else {
            kv.decodeBool(key)
        }
    }

    override fun putBoolean(key: String, value: Boolean) {
        kv.encode(key, value)
    }

    override fun getInt(key: String, defaultValueL: Int?): Int? {
        return if (defaultValueL != null) {
            kv.decodeInt(key, defaultValueL)
        } else {
            kv.decodeInt(key)
        }
    }

    override fun putInt(key: String, value: Int) {
        kv.encode(key, value)
    }

    override fun getString(key: String, defaultValueL: String?): String? {
        return kv.decodeString(key, defaultValueL)
    }

    override fun putString(key: String, value: String) {
        kv.encode(key, value)
    }
}