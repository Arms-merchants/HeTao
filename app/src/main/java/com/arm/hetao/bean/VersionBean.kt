package com.arm.hetao.bean

/**
 *    author : heyueyang
 *    time   : 2023/08/03
 *    desc   :
 *    version: 1.0
 */
data class VersionBean(
    val buildVersionNo: Int = 0,
    val buildUpdateDescription: String = "",
    val needForceUpdate:Boolean = false,
    val downloadURL:String? = ""
)