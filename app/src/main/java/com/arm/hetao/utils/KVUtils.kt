package com.arm.hetao.utils

/**
 *    author : heyueyang
 *    time   : 2023/01/16
 *    desc   :
 *    version: 1.0
 */
object KVUtils {
    fun getKvManger(): KvInterface {
        return MMKVKv.instance
    }
}