package com.arm.hetao.ui.videoList

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.arm.hetao.bean.VideoListBean

/**
 *    author : heyueyang
 *    time   : 2023/08/04
 *    desc   :
 *    version: 1.0
 */
class VideoPageListAdapter(val play: (String) -> Unit, val download: (String) -> Unit) :
    PagingDataAdapter<VideoListBean, VideoPageListHolder>(diffCallback) {
    override fun onBindViewHolder(holder: VideoPageListHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPageListHolder {
        return VideoPageListHolder(parent).apply {
            itemView.setOnClickListener {
                getItem(absoluteAdapterPosition)?.videoUrl?.let {
                    play.invoke(it)
                }
            }
            tvDownload.setOnClickListener {
                getItem(absoluteAdapterPosition)?.videoUrl?.let {
                    download.invoke(it)
                }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<VideoListBean>() {
            override fun areItemsTheSame(oldItem: VideoListBean, newItem: VideoListBean): Boolean {
                return oldItem.videoUrl == newItem.videoUrl
            }

            override fun areContentsTheSame(
                oldItem: VideoListBean,
                newItem: VideoListBean
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}