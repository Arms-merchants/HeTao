package com.arm.hetao.ui.base

import androidx.fragment.app.Fragment
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
abstract class BaseFragment : Fragment() {

    private val loadingPopupView: LoadingPopupView by lazy {
        XPopup.Builder(requireContext())
            .asLoading("正在加载中")
    }

    fun showLoading() {
        loadingPopupView.show()
    }

    fun dismissLoading() {
        loadingPopupView.dismiss()
    }
}