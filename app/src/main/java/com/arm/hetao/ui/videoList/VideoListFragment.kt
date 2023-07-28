package com.arm.hetao.ui.videoList

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.arm.hetao.R
import com.arm.hetao.databinding.FragmentPlayListBinding
import com.arm.hetao.ui.play.PlayActivity
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 *    author : heyueyang
 *    time   : 2023/07/25
 *    desc   :
 *    version: 1.0
 */
class VideoListFragment : Fragment() {
    companion object {
        fun getInstance() = VideoListFragment()
    }

    private val mAdapter by lazy {
        VideoListAdapter(null)
    }
    private val viewModel: VideoListModel by viewModels()
    private lateinit var binding: FragmentPlayListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rv.adapter = mAdapter
        if (mAdapter.headerLayoutCount == 0) {
            mAdapter.addHeaderView(getHeaderView())
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val videoUrl = mAdapter.getItem(position).videoUrl
            toPlay(videoUrl)
        }
        viewModel.data.observe(viewLifecycleOwner) {
            binding.smartRefresh.finishRefresh()
            if (it == null || it.size < 10) {
                binding.smartRefresh.finishLoadMoreWithNoMoreData()
            } else {
                binding.smartRefresh.finishLoadMore()
            }
            mAdapter.setNewInstance(it)
        }
        binding.smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.getVideoList(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.getVideoList(false)
            }
        })

        viewModel.getVideoList(true)
    }

    private fun toPlay(url: String) {
        val intent = Intent(requireContext(), PlayActivity::class.java)
        intent.putExtra(
            "URL",
            url
        )
        startActivity(intent)
    }

    private fun getHeaderView(): View {
        val view = layoutInflater.inflate(R.layout.layout_header_view, null)
        view.findViewById<TextView>(R.id.tvName).text =
            "点击条目可以播放对应的视频"
        return view
    }

}