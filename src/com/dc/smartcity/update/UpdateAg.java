package com.dc.smartcity.update;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;

/**
 * 更新
 *
 * @author szsm
 */
public class UpdateAg {
    /**
     * 检测更新
     *
     * @param context
     */
    public static void update(Context context, String result) {
        if (TextUtils.isEmpty(result)) {
            return;
        }
        final UpdateBean updateBean = JSON.parseObject(result, UpdateBean.class);

        if (null == updateBean) {
            return;
        }
    }
}