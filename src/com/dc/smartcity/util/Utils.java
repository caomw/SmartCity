package com.dc.smartcity.util;

import android.content.Context;
import android.widget.Toast;

public class Utils {


    public static void showToast(CharSequence message, Context context) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context.getApplicationContext(), message, duration);
        toast.show();
    }


}
