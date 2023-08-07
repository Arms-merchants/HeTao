package com.arm.hetao.utils

import android.content.Context
import com.arm.hetao.BuildConfig
import com.arm.hetao.R
import com.arm.hetao.bean.VersionBean
import com.arm.hetao.config.Config
import com.open.hule.library.entity.AppUpdate
import com.open.hule.library.utils.UpdateManager

/**
 *    author : heyueyang
 *    time   : 2023/08/04
 *    desc   :
 *    version: 1.0
 */
object AppUpdateUtils {

    fun handleUpdate(context: Context, versionBean: VersionBean) {
        if (versionBean.buildVersionNo <= BuildConfig.VERSION_CODE) {
            return
        }
        val appUpdate = AppUpdate.Builder()
            .newVersionUrl(
                versionBean.downloadURL ?: Config.Http.UPDATE_URL
            )
            .updateTitle(R.string.update_title)
            .updateInfo(versionBean.buildUpdateDescription)
            .isSilentMode(false)
            .forceUpdate(if (versionBean.needForceUpdate) 1 else 0)
            .build()
        UpdateManager().startUpdate(context, appUpdate)
    }
}