package com.open.hule.library.downloadmanager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.open.hule.library.utils.UpdateManager;

import java.io.File;
import java.util.Objects;

/**
 * @author hule
 * @date 2019/7/11 10:34
 * 这个只有在自动安装的时候才会有效。。。去除响应的注册就可以了
 */
public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && UpdateManager.isAutoInstall) {
            if (Objects.requireNonNull(intent.getAction()).equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                // 下载完成
                long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                // 自动安装app
                installApp(context, downloadId);
            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                // 未下载完成，点击跳转系统的下载管理界面
                Intent viewDownloadIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                viewDownloadIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(viewDownloadIntent);
            }
        }
    }

    /**
     * 安装app
     *
     * @param context    上下文
     * @param downloadId 下载任务id
     */
    private void installApp(Context context, long downloadId) {
        try {
            if (downloadId == -1) {
                return;
            }
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Query query = new DownloadManager.Query();
            Cursor cursor = downloadManager.query(query.setFilterById(downloadId));
            if (cursor != null && cursor.moveToFirst()) {
                String fileUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                String path = Uri.parse(fileUri).getPath();
                cursor.close();
                if (!TextUtils.isEmpty(path)) {
                    File apkFile = new File(path);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                    } else {
                        //Android7.0之后获取uri要用contentProvider
                        Uri apkUri = FileProvider.getUriForFile(context.getApplicationContext(), context.getPackageName() + ".fileProvider", apkFile);
                        //Granting Temporary Permissions to a URI
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
