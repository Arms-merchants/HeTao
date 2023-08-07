package com.arm.hetao.bean

data class DataContent(
    val circle_resource: List<CircleResource>? = null,
    val exchange_content: String? = "",
    val exchange_id: String? = "",
    val exchange_imghead: String? = "",
    val exchange_isMyself: Boolean? = false,
    val exchange_nickname: String? = "",
    val exchange_sender: String? = "",
    val exchange_sendtime: Long? = 0L,
    val exchange_sendtype: String? = "",
    val exchange_song: String? = "",
    val isshare: String? = "",
    val readnum: Int? = 0
)