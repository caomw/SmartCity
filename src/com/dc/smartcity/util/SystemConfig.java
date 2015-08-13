package com.dc.smartcity.util;

import com.android.dcone.ut.net.impl.HttpTaskFactory;

public class SystemConfig {


    /**
     * debug模式
     */
    public static final boolean DEBUG = true;

    /**
     * 包名
     */
    public static final String PACKAGENAME="com.dc.smartcity";


    public static HttpTaskFactory.Type TYPE_HTTP_TASKER = HttpTaskFactory.Type.OK_HTTP;

    public static HttpTaskFactory.Type getHttpTaskType(){
        return TYPE_HTTP_TASKER == null ? HttpTaskFactory.Type.URL_HTTP : TYPE_HTTP_TASKER;
    }
}
