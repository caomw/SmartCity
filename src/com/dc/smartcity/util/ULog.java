package com.dc.smartcity.util;

import java.util.Locale;

public class ULog {
    public static String TAG = SystemConfig.PACKAGENAME;

    /**
     * ������־������
     *
     * @param tag ��־���
     */
    public static void setTag(String tag) {
        debug(tag, "Changing log tag to %s", tag);
        TAG = tag;
    }

    /**
     * ���verbose������־
     *
     * @param format ��־��ʽ
     * @param args   �滻����
     */
    public static void verbose(String format, Object... args) {
        if (SystemConfig.DEBUG) {
            android.util.Log.v(TAG, buildMessage(format, args));
        }
    }

    /**
     * ���debug������־
     *
     * @param format ��־��ʽ
     * @param args   �滻����
     */
    public static void debug(String format, Object... args) {
        if (SystemConfig.DEBUG) {
            android.util.Log.d(TAG, buildMessage(format, args));
        }
    }

    /**
     * ���info������־
     *
     * @param format ��־��ʽ
     * @param args   �滻����
     */
    public static void info(String format, Object... args) {
        if (SystemConfig.DEBUG) {
            android.util.Log.i(TAG, buildMessage(format, args));
        }
    }

    /**
     * ���error������־
     *
     * @param format ��־��ʽ
     * @param args   �滻����
     */
    public static void error(String format, Object... args) {
        String msg = buildMessage(format, args);
        android.util.Log.e(TAG, msg);
    }

    /**
     * ���error������־
     *
     * @param tr     �쳣
     * @param format ��־��ʽ
     * @param args   �滻����
     */
    public static void error(Throwable tr, String format, Object... args) {
        String msg = buildMessage(format, args);
        android.util.Log.e(TAG, msg, tr);
    }

    /**
     * ���warn������־
     *
     * @param format ��־��ʽ
     * @param args   �滻����
     */
    public static void warn(String format, Object... args) {
        android.util.Log.w(TAG, buildMessage(format, args));
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null || (args != null && args.length <= 0)) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(ULog.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }
}
