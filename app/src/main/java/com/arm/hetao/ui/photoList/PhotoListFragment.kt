package com.arm.hetao.ui.photoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arm.hetao.databinding.FragmentPhotoListBinding
import com.arm.hetao.ui.base.BaseFragment

/**
 *    author : heyueyang
 *    time   : 2023/07/28
 *    desc   :
 *    version: 1.0
 */
class PhotoListFragment : BaseFragment() {
    companion object {
        fun getInstance() = PhotoListFragment()
    }

    private lateinit var binding: FragmentPhotoListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotoListBinding.inflate(inflater)
        return binding.root
    }


}