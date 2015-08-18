package com.dc.smartcity.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.dc.smartcity.bean.UserObj;

public class Utils {
    public static UserObj user;//登录的用户信息
    public static DisplayMetrics dm;


    public static void showToast(CharSequence message, Context context) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context.getApplicationContext(), message, duration);
        toast.show();
    }

    public static DisplayMetrics getMetrics(Activity activity) {
        if (null == dm) {
            dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        return dm;
    }

    /**
     * 是否登陆
     *
     * @return
     */
    public static boolean isLogin() {
        if (user == null)
            return false;
        return true;
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static UserObj getUserInfo() {
        return user;
    }


    private static String accessTicket;
    
    public static void setAccessTicket(String accessTick){
    	accessTicket = accessTick;
    }
    
    public static String getAccessTicket(){
    	return accessTicket;
    }
}
