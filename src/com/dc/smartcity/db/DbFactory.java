package com.dc.smartcity.db;

import android.content.Context;
import com.android.dcone.ut.DbUtils;
import com.dc.smartcity.util.SystemConfig;

/**
 * 创建dao
 */
public class DbFactory {
    public static DbUtils getDBUtils(Context context) {
        DbUtils db = DbUtils.create(context);
        db.configAllowTransaction(true);
        db.configDebug(SystemConfig.DEBUG);
        return db;
    }
}
