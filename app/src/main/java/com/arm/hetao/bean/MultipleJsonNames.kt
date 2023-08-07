package com.arm.hetao.bean

import com.squareup.moshi.JsonQualifier

/**
 *    author : heyueyang
 *    time   : 2023/08/04
 *    desc   :
 *    version: 1.0
 */
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class MultipleJsonNames(val value:Array<String>)