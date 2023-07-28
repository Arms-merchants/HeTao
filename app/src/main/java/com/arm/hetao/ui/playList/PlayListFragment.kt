package com.arm.hetao.ui.playList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arm.hetao.R
import com.arm.hetao.bean.ViewPageItemBean
import com.arm.hetao.bean.ViewPagePlayData
import com.arm.hetao.databinding.FragmentPlayListBinding
import com.arm.hetao.ui.base.BaseFragment
import com.arm.hetao.ui.play.PlayListActivity

/**
 *    author : heyueyang
 *    time   : 2023/07/25
 *    desc   :
 *    version: 1.0
 */
class PlayListFragment : BaseFragment() {

    private lateinit var binding: FragmentPlayListBinding
    private lateinit var model: PlayListViewModel

    private val mAdapter by lazy {
        PlayListAdapter(null)
    }

    companion object {
        fun getInstance() = PlayListFragment()
    }

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
        model = ViewModelProvider(this)[PlayListViewModel::class.java]
        model.data.observe(viewLifecycleOwner) {
            binding.smartRefresh.finishRefresh()
            mAdapter.setNewInstance(it)
        }
        binding.rv.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.rv.adapter = mAdapter
        mAdapter.setOnItemClickListener { adapter, view, position ->
            toList(mAdapter.data, position)
        }
        if (mAdapter.headerLayoutCount == 0) {
            val view = layoutInflater.inflate(R.layout.layout_header_view, null)
            view.findViewById<TextView>(R.id.tvName).text =
                "现在在播放的界面可以上下滑动来切换各个监控"
            mAdapter.addHeaderView(view)
        }
        binding.smartRefresh.setEnableLoadMore(false)
        binding.smartRefresh.setOnRefreshListener {
            Log.e("TAG", "smartRefresh")
            model.getData()
        }
        model.getData()
    }

    private fun toList(data: List<Pair<String, String>>, position: Int) {
        val list = arrayListOf<ViewPageItemBean>()
        data.forEach {
            val item = ViewPageItemBean(it.first, it.second)
            list.add(item)
        }
        val jumpData = ViewPagePlayData(position, list)
        val intent = Intent(requireContext(), PlayListActivity::class.java)
        intent.putExtra(PlayListActivity.DATA, jumpData)
        startActivity(intent)
    }

}