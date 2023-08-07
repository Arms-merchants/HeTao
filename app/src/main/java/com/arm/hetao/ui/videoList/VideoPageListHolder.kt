package com.arm.hetao.ui.videoList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arm.hetao.R
import com.arm.hetao.bean.VideoListBean

/**
 *    author : heyueyang
 *    time   : 2023/08/04
 *    desc   :
 *    version: 1.0
 */
class VideoPageListHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
) {

    private val ivCover = itemView.findViewById<ImageView>(R.id.ivCover)
    private val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
    private val tvContent = itemView.findViewById<TextView>(R.id.tvContent)
    val tvDownload = itemView.findViewById<TextView>(R.id.tvDownload)

    fun bindTo(item: VideoListBean?) {
        item?.let {
            ivCover.load(it.coverUrl)
            tvTime.text = it.time
            tvContent.text = it.content
        }
    }


}