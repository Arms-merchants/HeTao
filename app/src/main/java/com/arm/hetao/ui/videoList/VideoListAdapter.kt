package com.arm.hetao.ui.videoList

import android.widget.ImageView
import coil.load
import com.arm.hetao.R
import com.arm.hetao.bean.VideoListBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
class VideoListAdapter(data: MutableList<VideoListBean>?) :
    BaseQuickAdapter<VideoListBean, BaseViewHolder>(R.layout.item_video, data) {

    override fun convert(holder: BaseViewHolder, item: VideoListBean) {
        holder.getView<ImageView>(R.id.ivCover).load(item.coverUrl)
        holder.setText(R.id.tvTime, item.time)
        holder.setText(R.id.tvContent, item.content)
    }
}