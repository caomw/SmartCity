package com.dc.smartcity.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.dc.smartcity.bean.user.UserObj;
import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static String accessTicket;
    public static UserObj user;// 登录的用户信息
    public static DisplayMetrics dm;

    public static void showToast(CharSequence message, Context context) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context.getApplicationContext(), message,
                duration);
        toast.show();
    }

    public static DisplayMetrics getMetrics(Activity activity) {
        if (null == dm) {
            dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        return dm;
    }

    public static String mixPhone(String mobile) {
        if (TextUtils.isEmpty(mobile) || mobile.length() != 11) {
            return mobile;
        }
        return mobile.replace(mobile.substring(3, 7), "****");
    }

    public static void logout() {
        accessTicket = null;
        user = null;
    }

    /**
     * 是否登陆
     *
     * @return
     */
    public static boolean isLogon() {
        return !TextUtils.isEmpty(accessTicket) && null != user;
    }

    public static void clearUserData() {
        accessTicket = "";
        user = null;
    }

    public static String BitmapToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();

            byte[] imgBytes = out.toByteArray();
            return new String(Base64.encodeBase64(imgBytes));
        } catch (Exception e) {
            return null;
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void setAccessTicket(String accessTick) {
        accessTicket = accessTick;
    }

    public static String getAccessTicket() {
        return accessTicket;
    }

    public static String formatDate(String timpstam) {
        if (TextUtils.isEmpty(timpstam)) {
            return "";
        }
        long time = (long) Double.parseDouble(timpstam);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(time);
        return sdf.format(date);
    }

    public static String formatDateTime(String timpstam) {
        long time = (long) Double.parseDouble(timpstam);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(time);
        return sdf.format(date);
    }
}
