package com.arm.hetao.ui.newMenuList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arm.hetao.databinding.FragmentNewMenuListBinding
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

/**
 *    author : heyueyang
 *    time   : 2023/07/25
 *    desc   :
 *    version: 1.0
 */
class NewMenuListFragment : Fragment() {

    companion object {
        fun getInstance() = NewMenuListFragment()
    }

    private lateinit var model: NewMenuListModel
    private val mAdapter by lazy {
        NewMenuListAdapter(null)
    }

    private lateinit var binding: FragmentNewMenuListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMenuListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this)[NewMenuListModel::class.java]
        model.data.observe(viewLifecycleOwner) {
            binding.smartRefresh.finishRefresh()
            binding.smartRefresh.finishLoadMore()
            if (model.isCurrentDay()) {
                mAdapter.setNewInstance(it)
            } else {
                if (it != null) {
                    mAdapter.addData(it)
                }
            }
        }
        binding.rv.adapter = mAdapter
        model.getData(true)
        binding.smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onRefresh(refreshLayout: RefreshLayout) {
                model.getData(true)
            }

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                model.getData(false)
            }

        })
    }


}