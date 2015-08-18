package com.dc.smartcity.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class Utils {
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


    private static String accessTicket;
    
    public static void setAccessTicket(String accessTick){
    	accessTicket = accessTick;
    }
    
    public static String getAccessTicket(){
    	return accessTicket;
    }
}
