package com.arm.hetao.bean

/**
 * 单个教室的视频监控
 */
data class ChannelItemBean(
    val channel: List<Channel>,
    val name: String,
    val openTimes: String,
    val state: String
)