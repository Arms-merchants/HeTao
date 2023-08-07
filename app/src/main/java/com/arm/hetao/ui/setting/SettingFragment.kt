package com.arm.hetao.ui.setting

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.arm.hetao.BuildConfig
import com.arm.hetao.databinding.FragmentSettingBinding
import com.arm.hetao.ext.showSnackBar
import com.arm.hetao.ui.base.BaseFragment
import com.arm.hetao.utils.AppUpdateUtils
import com.google.android.material.snackbar.Snackbar

/**
 *    author : heyueyang
 *    time   : 2023/07/26
 *    desc   :
 *    version: 1.0
 */
class SettingFragment : BaseFragment() {

    companion object {
        fun getInstance() = SettingFragment()
    }

    private lateinit var binding: FragmentSettingBinding
    private lateinit var viewModel: SettingModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SettingModel::class.java]
        binding.tvLogin.setOnClickListener {
            login()
        }
        viewModel.data.observe(viewLifecycleOwner) {
            dismissLoading()
            Snackbar.make(binding.root, "登录成功", Snackbar.LENGTH_LONG).show()
        }
        binding.tvVersion.text = "应用版本:${BuildConfig.VERSION_NAME}"
        binding.llUpdate.setOnClickListener {
            showLoading()
            viewModel.checkVersion()
        }
        viewModel.version.observe(viewLifecycleOwner) {
            dismissLoading()
            it?.let {
                if (it.buildVersionNo <= BuildConfig.VERSION_CODE) {
                    "已经是最新版本".showSnackBar(binding.tvVersion)
                    return@observe
                }
                AppUpdateUtils.handleUpdate(requireContext(), it)
            }
        }
    }

    private fun login() {
        if (TextUtils.isEmpty(binding.etPhone.text.toString())) {
            Snackbar.make(binding.root, "电话号码为空", Snackbar.LENGTH_LONG).show()
            return
        }
        if (TextUtils.isEmpty(binding.etPassword.text.toString())) {
            Snackbar.make(binding.root, "密码为空", Snackbar.LENGTH_LONG).show()
            return
        }
        showLoading()
        viewModel.login(binding.etPhone.text.toString(), binding.etPassword.text.toString())
    }

}