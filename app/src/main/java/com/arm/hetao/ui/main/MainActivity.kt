package com.arm.hetao.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.arm.hetao.R
import com.arm.hetao.adapter.FragmentPagerAdapter
import com.arm.hetao.databinding.ActivityMainBinding
import com.arm.hetao.ext.showSnackBar
import com.arm.hetao.ui.newMenuList.NewMenuListFragment
import com.arm.hetao.ui.photoList.PhotoListFragment
import com.arm.hetao.ui.playList.PlayListFragment
import com.arm.hetao.ui.setting.SettingFragment
import com.arm.hetao.ui.videoList.VideoListFragment
import com.arm.hetao.utils.AppUpdateUtils


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val ids = arrayListOf<Int>(R.id.tab1, R.id.tab2, R.id.tab3, R.id.tab4, R.id.tab5)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.vp2.isUserInputEnabled = false
        setContentView(binding.root)
        viewModel.userInfo.observe(this) {
            if (it == null) {
                "请重新打开应用".showSnackBar(binding.vp2)
            } else {
                buildView()
            }
        }
        viewModel.getUserInfo()
        viewModel.getNewVersion()
        viewModel.versionData.observe(this) {
            it?.let {
                AppUpdateUtils.handleUpdate(this, it)
            }
        }
    }

    private fun buildView() {
        val fragments = arrayListOf<Fragment>()
        fragments.add(PlayListFragment.getInstance())
        fragments.add(VideoListFragment.getInstance())
        fragments.add(NewMenuListFragment.getInstance())
        fragments.add(PhotoListFragment.getInstance())
        fragments.add(SettingFragment.getInstance())
        val adapter = FragmentPagerAdapter(supportFragmentManager, lifecycle, fragments)
        binding.vp2.adapter = adapter
        binding.bottomNavigation.setOnItemSelectedListener {
            val index = ids.indexOf(it.itemId)
            if (index != -1) {
                binding.vp2.currentItem = index
                true
            } else {
                false
            }
        }
    }


}

