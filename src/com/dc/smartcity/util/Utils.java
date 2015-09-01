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

public class Utils {
    public static String accessTicket;//登录之后的凭证
    public static UserObj user;// 登录的用户信息
    public static DisplayMetrics dm;//屏幕宽高

    /**
     * 弹出toast
     *
     * @param message
     * @param context
     */
    public static void showToast(CharSequence message, Context context) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context.getApplicationContext(), message, duration);
        toast.show();
    }

    /**
     * 获取屏幕宽高度
     *
     * @param activity
     * @return
     */
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

    /**
     * 退出登录，删除保存的用户信息
     */
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

    /**
     * 是否实名认证
     *
     * @return
     */
    public static boolean isRealName() {
        if (null == user) {
            return false;
        }
        return (!"01".equals(user.userBase.level) && !"0201".equals(user.userBase.level));
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


    public static String getAccessTicket() {
        return accessTicket;
    }

}
