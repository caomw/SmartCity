package com.dc.smartcity.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.dc.smartcity.bean.user.UserObj;


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

    
    public static String mixPhone(String mobile){
    	if(TextUtils.isEmpty(mobile) || mobile.length() != 11){
    		return mobile;
    	}
    	return mobile.replace(mobile.substring(3, 7), "****");
    }

    /**
     * 是否登陆
     * @return
     */
    public static boolean isLogon(){
    	if(TextUtils.isEmpty(accessTicket)){
    		return false;
    	}
    	return true;
    }

    private static String accessTicket;
    
    public static void setAccessTicket(String accessTick){
    	accessTicket = accessTick;
    }
    
    public static String getAccessTicket(){
    	return accessTicket;
    }
    
    public static String formatDate(String timpstam){
    	long time = (long)Double.parseDouble(timpstam);
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date date = new Date(time);
		return sdf.format(date);
    }
    public static String formatDateTime(String timpstam){
    	long time = (long)Double.parseDouble(timpstam);
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
    	Date date = new Date(time);
    	return sdf.format(date);
    }
}
