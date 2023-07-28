package com.arm.hetao.ui.menuList

import android.widget.ImageView
import coil.load
import com.arm.hetao.R
import com.arm.hetao.bean.MenuListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *    author : heyueyang
 *    time   : 2023/07/27
 *    desc   :
 *    version: 1.0
 */
class MenuListAdapter(data: MutableList<MenuListBean>?) :
    BaseQuickAdapter<MenuListBean, BaseViewHolder>(R.layout.item_menu, data) {

    override fun convert(holder: BaseViewHolder, item: MenuListBean) {
        holder.setText(R.id.tvName, item.cbName)
        holder.getView<ImageView>(R.id.ivImg).apply {
            load(item.cbImg) {
                crossfade(true)
                //transformations(CircleCropTransformation())
            }
        }
    }
}