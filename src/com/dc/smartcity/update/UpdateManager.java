/**
 *
 * 基于 Cobub Razor 框架，更新增加APP版本更新和H5资源包更新
 *
 * @package		Cobub Razor
 * @author		WBTECH Dev Team
 * @copyright	Copyright (c) 2011 - 2012, NanJing Western Bridge Co.,Ltd.
 * @since		Version 0.1
 * @filesource
 */

package com.dc.smartcity.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.dc.smartcity.util.ULog;
import com.tencent.tauth.IUiListener;

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

/**
 * 更新数据
 * 
 * @author check_000
 *
 */
public class UpdateManager {
	// String appkey;
	// public Context mContext;
	public String				force;
	public ProgressDialog		progressDialog;

	private final String		savePath		= "/sdcard/";

	private String				saveFile		= null;

	// private ProgressBar mProgress;

	private static final int	DOWN_UPDATE		= 1;

	private static final int	DOWN_OVER		= 2;
	// private static final int DOWN_H5_OVER = 3;

	private int					progress;

	private Thread				downLoadThread;

	private static boolean		interceptFlag	= false;
	// public String newVersion;
	// public String newtime;

	private Handler				mHandler		= new Handler() {
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

	private UpdateBean	ubean;
	private Context		context;

	private UpdateManager(Context context) {
		this.context = context;
		// 初始化存储地址
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			// 首先创建一个路径 ,路径的创建在android1.6以上，低版本路径为/sdcard

			// 通过web得到路径，放置有的版本不一致而出错
			File dirPath = Environment.getExternalStorageDirectory();
			// 通过日志的方式输出，我们可在File Explorer中看到输出
			saveFile = dirPath.toString() + "/";
		}
		else {
			saveFile = savePath;
		}
	}

	public void checkUpdate(UpdateBean ubean) {
		this.ubean = ubean;

	}

	private static UpdateManager	instance;

	public static UpdateManager getInstance(Context context) {
		if (null == instance) {
			instance = new UpdateManager(context);
		}
		return instance;
	}

	private Dialog	noticeDialog;

	/**
	 * 更新提示对话框
	 */
	public void showNoticeDialog(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("更新提示");
		builder.setMessage(ubean.versionDetail);
		builder.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog(context);
			}

		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// if ("true".equals(ubean.getForce_update())) {
				// System.exit(0);
				// } else {
				dialog.dismiss();
				// }
			}
		});

		// if(null == noticeDialog){

		Dialog noticeDialog = builder.create();
		// }
		noticeDialog.show();
	}

	public void showSdDialog(final Context context) {
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
	 * @param version
	 */
	private void showDownloadDialog(Context context) {
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

	private Runnable	mdownFileRunnable	= new Runnable() {
												@Override
												public void run() {
													try {
														Log.e("UpdateManager", "downUrl:" + downUrl);
														URL url = new URL(downUrl);

														HttpURLConnection conn = (HttpURLConnection) url.openConnection();
														conn.connect();
														int length = conn.getContentLength();
														InputStream is = conn.getInputStream();

														// File file = new File(downUrl);
														boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
														if (!sdCardExist) {
															showSdDialog(context);
														}
														else {

															saveFile = saveFile + downUrl.substring(downUrl.lastIndexOf("/") + 1);
															File ApkFile = new File(saveFile);
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

	private String		downUrl;

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
	 * @param url
	 */
	private void installApk() {
		ULog.error("saveFile: " + saveFile);
		File apkfile = new File(saveFile);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");

		// 安装，如果签名不一致，可能出现程序未安装提示
		//		i.setDataAndType(Uri.fromFile(new File(apkfile.getAbsolutePath())),
		//				"application/vnd.android.package-archive");

		context.startActivity(i);

	}

}
