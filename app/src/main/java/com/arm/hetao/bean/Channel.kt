package com.arm.hetao.bean

/**
 * 视频监控的名称和状态、地址
 */
data class Channel(
    val name: String,
    val online: String,
    val sn: String,
    val snpass: String,
    val url: String
)