package com.arm.hetao.ext

/**
 *    author : heyueyang
 *    time   : 2023/01/17
 *    desc   :
 *    version: 1.0
 */
fun String?.isEmpty(): Boolean {
    return this == null || this.trim().length == 0
}