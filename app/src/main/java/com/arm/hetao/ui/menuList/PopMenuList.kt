package com.arm.hetao.ui.menuList

import android.content.Context
import com.arm.hetao.R
import com.arm.hetao.bean.MenuListBean
import com.arm.hetao.databinding.PopMenuListBinding
import com.lxj.xpopup.core.BottomPopupView


/**
 *    author : heyueyang
 *    time   : 2023/07/27
 *    desc   :
 *    version: 1.0
 */
class PopMenuList(context: Context, val timeStr: String, val data: MutableList<MenuListBean>) :
    BottomPopupView(context) {

    override fun getImplLayoutId(): Int {
        return R.layout.pop_menu_list
    }

    override fun onCreate() {
        super.onCreate()
        val binding: PopMenuListBinding = PopMenuListBinding.bind(popupImplView)
        val adapter = MenuListAdapter(data)
        binding.rv.adapter = adapter
        binding.ivClose.setOnClickListener { dismiss() }
        binding.tvTime.text = timeStr
    }

}