package com.dc.smartcity.base;

import java.util.Stack;

import com.android.dcone.ut.wbtech.ums.UmsAgent;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

public class BaseApplication extends Application {

	private static BaseApplication		instance;

	static Stack<BaseActionBarActivity>	mRunAct;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		mRunAct = new Stack<BaseActionBarActivity>();
		// // 测试
		// UpdateAgent.init(UpdateMode.MODE_APP);
		// // 不使用assert目录，所以文件名为null
		// UpdateAgent.initH5Pro(this, null, false);
		
		UmsAgent.setBaseURL("http://m.dceast.cn:81/app_finder/");
		UmsAgent.setDefaultReportPolicy(this, 1);
		UmsAgent.postDevice(this);
		UmsAgent.onError(this);
	}

	public static BaseApplication getInstance() {
		return instance;
	}

	public void onActCreate(BaseActionBarActivity act) {
		if (!mRunAct.contains(act)) {
			mRunAct.add(act);
		}
	}

	public void onActDestroy(BaseActionBarActivity act) {
		if (mRunAct.contains(act)) {
			mRunAct.remove(act);
		}
	}

	// 退出程序
	// sassafras
	public void exit(boolean isExit) {
		if (isExit) {
			final int apiLevel = Build.VERSION.SDK_INT;
			ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
			if (apiLevel > 7) {
				String packageName = this.getPackageName();
				am.killBackgroundProcesses(packageName);
			}

			for (BaseActionBarActivity m : mRunAct) {
				m.finish();
			}

			android.os.Process.killProcess(android.os.Process.myPid());
		}

	}
}
