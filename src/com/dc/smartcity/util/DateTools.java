package com.dc.smartcity.util;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * ���ڹ�����
 */
public class DateTools {
    // �������������ʽ
    public final static String YYYYMMDD = "yyyyMMdd";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_M_D = "yyyy-M-d";
    public final static String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String HHMMSS = "HHmmss";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String MM_SS = "MM��dd��";
    public final static String YYYY_MM_DD_EE = "yyyy-MM-dd EE";
    public final static String YYYY_MM_DD_EEEE = "yyyy-MM-dd EEEE";
    public final static String DATE_FORMAT_STR_CHINESE = "yyyy��MM��dd��";

    public static final SimpleDateFormat DATE_FORMAT_DEFAULT = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS,
            Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_DATE_YYYY_MM_DD = new SimpleDateFormat(YYYY_MM_DD,
            Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_MM_SS_ = new SimpleDateFormat(MM_SS, Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_YYYY_M_D = new SimpleDateFormat(YYYY_M_D, Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_EE = new SimpleDateFormat(YYYY_MM_DD_EE,
            Locale.getDefault());
    public static final SimpleDateFormat DATE_FORMAT_YYYY_MM_DD_EEEE = new SimpleDateFormat(YYYY_MM_DD_EEEE,
            Locale.getDefault());

    /**
     * ��ȡ���ʱ�� �ַ������� xxСʱxx��
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static String getHoursMinute2String(Calendar cal1, Calendar cal2) {
        long gap = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 60);// �������
        if (gap < 0) {
            gap = gap + 1000 * 60 * 60 * 24;
        }
        String tip = "";

        if (gap >= 60) {
            long l2 = gap / 60;
            long l3 = gap % 60;
            if (l3 > 0) {
                tip = l3 + "����";
            }

            tip = l2 + "Сʱ" + tip;

        } else if (gap > 0) {
            tip = gap + "����";
        }

        return tip;
    }

    /**
     * ����һ��ʱ�䣬��ȡ��ʱ���ʱ���
     *
     * @param @param  dateString
     * @param @return
     * @param @throws ParseException
     */
    public static long string2Timestamp(String dateString) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        long temp = date1.getTime();// JAVA��ʱ���������13λ
        return temp;
    }

    /**
     * ��ȡyyyy.MM.dd HH:mm:ss��ʽ�ĵ�ǰʱ��
     */
    public static String getNowDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        String date = format.format(c.getTime());
        return date;
    }

    /**
     * yyyy-MM-dd HH:mm:ss �л�ȡ yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(String date) {
        Date d = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ymdDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            d = sdfDate.parse(date);
        } catch (ParseException e) {
            return date;
        }

        return ymdDate.format(d);
    }

    /**
     * ��ȡ���� ʱ����Ϊ0
     *
     * @return
     */
    public static Calendar getCalendar() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Calendar c = Calendar.getInstance();
        String dateString = format.format(c.getTime());
        try {
            c.setTime(format.parse(dateString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static Calendar getCalendar(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = (Calendar) calendar.clone();
        String dateString = format.format(c.getTime());
        try {
            c.setTime(format.parse(dateString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * ��1900-01-01 07:00:00��ʽ��Ϊ07:00
     *
     * @param strTime
     * @return 07:00��ʽ
     */
    public static String formatTime(String strTime) {
        SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = f1.parse(strTime);
            f1 = new SimpleDateFormat("HH:mm");
            strTime = f1.format(date);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return strTime;
    }

    /**
     * ��ȡ�������
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static int getBetween(Calendar cal1, Calendar cal2) {
        long gap = (cal2.getTimeInMillis() - cal1.getTimeInMillis()) / (1000 * 3600 * 24);// �Ӽ�������ɼ������
        return (int) gap;
    }

    /**
     * ��ȡ������� ȥ�������ʱ���� ��ȡ�������
     *
     * @param cal1
     * @param cal2
     * @return
     */
    public static int getBetween2(Calendar cal1, Calendar cal2) {
        long gap = (getCalendar((Calendar) cal2.clone()).getTimeInMillis() - getCalendar(cal1).getTimeInMillis())
                / (1000 * 3600 * 24);// �Ӽ�������ɼ������
        return (int) gap;
    }

    public static boolean isToday(Calendar cal) {
        boolean ret = false;
        Calendar tmp = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String s1 = format.format(cal.getTime());
        String s2 = format.format(tmp.getTime());
        if (s1.equals(s2)) {
            ret = true;
        }
        return ret;
    }

    public static boolean isExpired(String searchStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        boolean ret = false;
        Calendar cur = DateTools.getCalendar();
        Calendar search = DateTools.getCalendar();
        try {
            search.setTime(format.parse(searchStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (search.before(cur)) {
            ret = true;
        }
        return ret;
    }

    /**
     * yyyy-MM-dd HH:mm:ss �ַ�����ʽ������ʱ��ļ���
     *
     * @param str1 ����
     * @param str2 ������
     * @return ���غ��뼶���ʱ�䵥λ
     */
    public static long getTimeTwoString(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(str1);
            d2 = df.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = d1.getTime() - d2.getTime();
        return time;
    }

    /**
     * �Ѻ��������ʱ���ʽת��Ϊ hh:mm:ss ��ʽ��ʱ��
     *
     * @param diff
     * @return
     */
    public static String twoDate(long diff) {
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
        long miao = (diff - days * (100 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;
        String str;
        if (String.valueOf(hours).length() == 1) {
            str = "0" + hours + ":";
        } else {
            str = hours + ":";
        }
        if (String.valueOf(minutes).length() == 1) {
            str = str + "0" + minutes + ":";
        } else {
            str = str + minutes + ":";
        }
        if (String.valueOf(miao).length() == 1) {
            str = str + "0" + miao;
        } else {
            str = str + miao;
        }
        return str;
    }


}
