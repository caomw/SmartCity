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

	static UpdateManager	manager;

	/**
	 * 检测更新
	 *
	 * @param context
	 */
	public static void update(Context context, String result, boolean notice) {
		if (TextUtils.isEmpty(result)) {
			return;
		}
		final UpdateBean updateBean = JSON.parseObject(result, UpdateBean.class);

		if (null == updateBean) {
			return;
		}
		manager = UpdateManager.getInstance(context);
		manager.checkUpdate(updateBean);
		if (updateBean.isUpdate) {
			manager.showNoticeDialog();
		}else if(notice){
			Utils.showToast("当前已经是最新版本", context);
		}
	}
}