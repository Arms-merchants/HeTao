package com.arm.hetao.ui.newMenuList

import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arm.hetao.R
import com.arm.hetao.bean.MenuListAdapterBean
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.util.SmartGlideImageLoader

/**
 *    author : heyueyang
 *    time   : 2023/07/28
 *    desc   :
 *    version: 1.0
 */
class NewMenuListAdapter(data: MutableList<MenuListAdapterBean>?) :
    BaseQuickAdapter<MenuListAdapterBean, BaseViewHolder>(R.layout.item_menu_list, data) {

    override fun convert(holder: BaseViewHolder, item: MenuListAdapterBean) {
        holder.setText(R.id.tvTime, item.time)
        item.content?.let {
            holder.setText(R.id.tvContent, it.content)
            val photoAdapter = NewMenuPhotoAdapter(null)
            holder.getView<RecyclerView>(R.id.rvPhoto).apply {
                layoutManager = GridLayoutManager(context, 4)
                adapter = photoAdapter
            }
            photoAdapter.setNewInstance(it.photos)
            photoAdapter.addChildClickViewIds(R.id.iv)
            photoAdapter.setOnItemChildClickListener { adapter, view, position ->
                val list: List<String> = arrayListOf<String>().apply {
                    if (it.photos != null) {
                        this.addAll(it.photos)
                    }
                }
                XPopup.Builder(context)
                    .asImageViewer(
                        view as ImageView, position,
                        list, { popupView, position ->
                            popupView.updateSrcView(
                                photoAdapter.getViewByPosition(
                                    position,
                                    R.id.iv
                                ) as ImageView
                            )
                        }, SmartGlideImageLoader()
                    ).show()
            }
        }
    }
}