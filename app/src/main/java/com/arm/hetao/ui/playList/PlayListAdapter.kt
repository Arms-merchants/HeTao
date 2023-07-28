package com.arm.hetao.ui.playList

import com.arm.hetao.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
class PlayListAdapter(data: MutableList<Pair<String, String>>?) :
    BaseQuickAdapter<Pair<String, String>, BaseViewHolder>(R.layout.item_list, data) {

    override fun convert(holder: BaseViewHolder, item: Pair<String, String>) {
        holder.setText(R.id.tv_name, item.first)
    }
}