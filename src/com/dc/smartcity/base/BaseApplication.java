package com.dc.smartcity.base;

import android.app.Application;
import com.android.dcone.ut.UpdateAgent;
import com.android.dcone.ut.update.UpdateMode;

public class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 测试
        UpdateAgent.init(UpdateMode.MODE_APP);
        // 不使用assert目录，所以文件名为null
        UpdateAgent.initH5Pro(this, null, false);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    // 退出程序
    //sassafras
    public void exit(boolean isExit) {
        if (isExit) {
            // final int apiLevel = Build.VERSION.SDK_INT;
            // ActivityManager am = (ActivityManager)
            // this.getSystemService(Context.ACTIVITY_SERVICE);
            // if (apiLevel > 7) {
            // String packageName = this.getPackageName();
            // am.killBackgroundProcesses(packageName);
            // }
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }
}
