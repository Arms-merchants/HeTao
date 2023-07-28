package com.arm.hetao.ui.play

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.arm.hetao.adapter.RecyclerItemNormalHolder
import com.arm.hetao.adapter.ViewPageAdapter
import com.arm.hetao.bean.ViewPagePlayData
import com.arm.hetao.databinding.ActivityPlasyListBinding
import com.shuyu.gsyvideoplayer.GSYVideoManager

/**
 *    author : heyueyang
 *    time   : 2023/02/17
 *    desc   :
 *    version: 1.0
 */
class PlayListActivity : ComponentActivity() {

    companion object {
        const val DATA = "Data"
    }

    private lateinit var binding: ActivityPlasyListBinding
    private lateinit var mAdapter: ViewPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlasyListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getParcelableExtra<ViewPagePlayData>(DATA)
        data?.apply {
            mAdapter = ViewPageAdapter(this@PlayListActivity, data.list)
            binding.vp2.orientation = ViewPager2.ORIENTATION_VERTICAL
            binding.vp2.adapter = mAdapter
            binding.vp2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val playPosition = GSYVideoManager.instance().playPosition
                    if (playPosition >= 0) {
                        if (GSYVideoManager.instance().playTag.equals(RecyclerItemNormalHolder.TAG)
                            && (position != playPosition)
                        ) {
                            GSYVideoManager.releaseAllVideos()
                            playPosition(position)
                        }
                    }
                }
            })
            binding.vp2.currentItem = position
            binding.vp2.post {
                playPosition(position)
            }
        }
    }

    private fun playPosition(position: Int) {
        binding.vp2.postDelayed({
            (binding.vp2.getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(
                position
            )?.let {
                (it as RecyclerItemNormalHolder).player.startPlayLogic()
            }
        }, 50)
    }

    override fun onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoManager.releaseAllVideos()
    }
}