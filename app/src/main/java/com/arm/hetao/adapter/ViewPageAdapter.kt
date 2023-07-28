package com.arm.hetao.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arm.hetao.R
import com.arm.hetao.bean.ViewPageItemBean

/**
 *    author : heyueyang
 *    time   : 2023/07/25
 *    desc   :
 *    version: 1.0
 */
class ViewPageAdapter(val context: Context, val items: List<ViewPageItemBean>? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_viewpage2_item, parent, false)
        return RecyclerItemNormalHolder(context, view)
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecyclerItemNormalHolder).apply {
            setRecyclerBaseAdapter(this@ViewPageAdapter)
            items?.get(position)?.let {
                onBind(position, it)
            }
        }
    }

}