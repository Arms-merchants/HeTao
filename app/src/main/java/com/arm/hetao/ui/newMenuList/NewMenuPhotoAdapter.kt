package com.arm.hetao.ui.newMenuList

import android.widget.ImageView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.arm.hetao.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *    author : heyueyang
 *    time   : 2023/07/28
 *    desc   :
 *    version: 1.0
 */
class NewMenuPhotoAdapter(data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_menu_photo, data) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getView<ImageView>(R.id.iv).load(item) {
            transformations(RoundedCornersTransformation(20f))
        }
    }

}