package com.arm.hetao.bean

/**
 * 视频监控的列表
 */
data class BaseResponseBean<T>(
    val dataContent: T?,
    val dataType: String
)