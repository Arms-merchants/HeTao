package com.arm.hetao

import android.app.Application
import cn.leancloud.LeanCloud
import com.arm.hetao.utils.KVUtils

/**
 *    author : heyueyang
 *    time   : 2023/01/13
 *    desc   :
 *    version: 1.0
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KVUtils.getKvManger().init(this)
        LeanCloud.initialize(
            "QWnRhHD6IlePo6NdPnS0gqyR-gzGzoHsz",
            "xYeAcQKBmc2uIZkYZTi0ad2q",
            "https://qwnrhhd6.lc-cn-n1-shared.com"
        )
    }
}