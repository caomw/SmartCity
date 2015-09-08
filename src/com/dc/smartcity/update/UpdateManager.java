/**
 * 基于 Cobub Razor 框架，更新增加APP版本更新和H5资源包更新
 *
 * @package Cobub Razor
 * @author WBTECH Dev Team
 * @copyright Copyright (c) 2011 - 2012, NanJing Western Bridge Co.,Ltd.
 * @filesource
 * @since Version 0.1
 */

package com.dc.smartcity.update;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 更新数据
 *
 * @author check_000
 *
 */
public class UpdateManager {
    public ProgressDialog progressDialog;

    private final String savePath = "/sdcard/";

    private String saveFile = null;


    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;

    private static boolean interceptFlag = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    progressDialog.setProgress(progress);
                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                default:
                    break;
            }
        }

    };


    public String now() {
        Time localTime = new Time("Asia/Beijing");
        localTime.setToNow();
        return localTime.format("%Y-%m-%d");
    }

    // private String nametimeString = now();

    private UpdateBean ubean;
    private Context context;

    private UpdateManager(Context context) {
        this.context = context;
        // 初始化存储地址
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            // 首先创建一个路径 ,路径的创建在android1.6以上，低版本路径为/sdcard

            // 通过web得到路径，放置有的版本不一致而出错
            File dirPath = Environment.getExternalStorageDirectory();
            // 通过日志的方式输出，我们可在File Explorer中看到输出
            saveFile = dirPath.toString() + "/";
        } else {
            saveFile = savePath;
        }
    }

    public void checkUpdate(UpdateBean ubean) {
        this.ubean = ubean;

    }

    private static UpdateManager instance;

    public static UpdateManager getInstance(Context context) {
        if (null == instance) {
            instance = new UpdateManager(context);
        }
        return instance;
    }

    private Dialog noticeDialog;

    /**
     * 更新提示对话框
     */
    public void showNoticeDialog() {
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle("更新提示");
        builder.setMessage(ubean.versionDetail);
        builder.setPositiveButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();
            }

        });
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }

    public void showSdDialog() {
        AlertDialog.Builder builder = new Builder(context);
        builder.setTitle("提示");
        builder.setMessage("SD卡不存在");
        builder.setNegativeButton("确定", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    /**
     * 显示下载进度对话框
     *
     * @param context
     */
    private void showDownloadDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("更新应用");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton("取消", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                interceptFlag = true;

            }
        });
        progressDialog.show();
        downloadFile(ubean.downLoadUrl);

    }

    private Runnable mdownFileRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.e("UpdateManager", "downUrl:" + downUrl);
                URL url = new URL(downUrl);

                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
                if (!sdCardExist) {
                    showSdDialog();
                } else {

                    String apkFile = saveFile
                            + downUrl.substring(downUrl.lastIndexOf("/") + 1);
                    File ApkFile = new File(apkFile);
                    FileOutputStream fos = new FileOutputStream(ApkFile);

                    int count = 0;
                    byte buf[] = new byte[1024];

                    do {
                        int numread = is.read(buf);
                        count += numread;
                        progress = (int) (((float) count / length) * 100);

                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                        if (numread <= 0) {
                            progressDialog.dismiss();

                            mHandler.sendEmptyMessage(DOWN_OVER);
                            break;
                        }

                        fos.write(buf, 0, numread);
                    } while (!interceptFlag);

                    fos.close();
                    is.close();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private String downUrl;

    /**
     * 下载更新安装包
     *
     * @param durl
     *            下载url
     */
    public void downloadFile(String durl) {
        downUrl = durl;
        downLoadThread = new Thread(mdownFileRunnable);
        downLoadThread.start();
    }

    /**
     * install apk
     *
     */
    private void installApk() {
        File apkfile = new File(saveFile);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(i);

    }

}
