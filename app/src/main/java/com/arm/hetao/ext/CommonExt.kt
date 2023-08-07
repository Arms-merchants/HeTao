package com.arm.hetao.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 *    author : heyueyang
 *    time   : 2023/01/17
 *    desc   :
 *    version: 1.0
 */
fun String?.isEmpty(): Boolean {
    return this == null || this.trim().length == 0
}

fun String.showSnackBar(view: View) {
    Snackbar.make(
        view,
        this,
        Snackbar.LENGTH_LONG
    ).show()
}
