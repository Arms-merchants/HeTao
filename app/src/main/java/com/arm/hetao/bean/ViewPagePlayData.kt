package com.arm.hetao.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *    author : heyueyang
 *    time   : 2023/07/25
 *    desc   :
 *    version: 1.0
 */
@Parcelize
data class ViewPagePlayData(val position: Int, val list: ArrayList<ViewPageItemBean>) : Parcelable