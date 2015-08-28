package com.dc.smartcity.update;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.dc.smartcity.util.Utils;
/**
 * 更新
 * @author szsm
 *
 */
public class UpdateAg {
	private static Handler handler;

	public static void init() {
		HandlerThread localHandlerThread = new HandlerThread("UpdateAg");
		localHandlerThread.start();
		UpdateAg.handler = new Handler(localHandlerThread.getLooper());
	}

	/**
	 * 检测更新
	 * 
	 * @param context
	 */
	public static void update(final Context context, String result) {
		if (TextUtils.isEmpty(result)) {
			return;
		}
		final UpdateBean version = JSON.parseObject(result, UpdateBean.class);

		if (null == version) {
			return;
		}
		
		if(!version.isUpdate){
			return;
		}
		Runnable isupdateRunnable = new Runnable() {

			@Override
			public void run() {
				isUpdate(context, version);
			}
		};
		handler.post(isupdateRunnable);
	}

	private static void isUpdate(Context context, UpdateBean version) {

		UpdateManager manager = UpdateManager.getInstance(context);
		manager.checkUpdate(version);
		if (version.isUpdate) {
			manager.showNoticeDialog();
		}
	}
}
