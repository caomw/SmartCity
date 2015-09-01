package com.dc.smartcity.update;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.dc.smartcity.util.Utils;

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

        if (!updateBean.isUpdate) {
            Utils.showToast("已经是最新版本", context);
            return;
        }

        UpdateManager manager = UpdateManager.getInstance(context);
        manager.checkUpdate(updateBean);
        if (updateBean.isUpdate) {
            manager.showNoticeDialog(context);
        }
    }
}
