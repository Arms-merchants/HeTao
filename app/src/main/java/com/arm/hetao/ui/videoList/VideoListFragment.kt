package com.arm.hetao.ui.videoList

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.arm.hetao.R
import com.arm.hetao.databinding.FragmentPlayListBinding
import com.arm.hetao.ext.showSnackBar
import com.arm.hetao.ui.play.PlayActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

    private val mNewAdapter =
        VideoPageListAdapter(play = this::toPlay, download = {
            getDownloadPermission(this::downloadVideo, it)
        })
    private val viewModel: VideoListModel by viewModels()
    private lateinit var binding: FragmentPlayListBinding
    private lateinit var downloadManager: DownloadManager

    private val onComplete: BroadcastReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent == null) return
                val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadId <= -1L) {
                    return
                }
                val query = DownloadManager.Query()
                query.setFilterById(downloadId)
                val cursor = downloadManager.query(query)
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                        val uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                        if (uriIndex < 0) {
                            cursor.close()
                            return
                        }
                        val uriStr =
                            cursor.getString(uriIndex)
                        MediaScannerConnection.scanFile(
                            requireContext(),
                            arrayOf(uriStr),
                            null,
                            null
                        )
                    }
                }
                cursor.close()
            }
        }
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
        downloadManager =
            requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        requireActivity().registerReceiver(
            onComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        binding.rv.adapter = mNewAdapter
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                mNewAdapter.submitData(it)
            }
        }
        mNewAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.smartRefresh.finishRefresh()
                }

                else -> {}
            }
        }
        binding.tipView.apply {
            root.visibility = View.VISIBLE
            tvName.text = "点击条目可以播放对应的视频"
        }
        binding.smartRefresh.setEnableLoadMore(false)
        binding.smartRefresh.setOnRefreshListener {
            mNewAdapter.refresh()
        }
    }

    private fun toPlay(url: String) {
        val intent = Intent(requireContext(), PlayActivity::class.java)
        intent.putExtra(
            "URL",
            url
        )
        startActivity(intent)
    }

    private fun downloadVideo(url: String) {
        "开始下载视频".showSnackBar(binding.rv)
        val fileName = url.substringAfterLast("/")
        val request = DownloadManager.Request(Uri.parse(url))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setAllowedOverRoaming(false)
            .setTitle("下载视频")
            .setDescription("正在下载视频")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_MOVIES, fileName)
        val id = downloadManager.enqueue(request)
    }

    private fun getDownloadPermission(downloadVideo: (String) -> Unit, url: String) {
        XXPermissions.with(this)
            .permission(
                Manifest.permission.READ_MEDIA_IMAGES
            )
            .permission(
                Manifest.permission.READ_MEDIA_VIDEO
            ).permission(
                Manifest.permission.READ_MEDIA_AUDIO
            )
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (!allGranted) {
                        "有部分权限没有正常授予".showSnackBar(binding.rv)
                        return
                    }
                    downloadVideo.invoke(url)
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    super.onDenied(permissions, doNotAskAgain)
                    if (doNotAskAgain) {
                        "被永久拒绝授权，请手动授予".showSnackBar(binding.rv)
                        XXPermissions.startPermissionActivity(requireContext(), permissions)
                    } else {
                        "获取权限失败".showSnackBar(binding.rv)
                    }
                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(onComplete)
    }
}