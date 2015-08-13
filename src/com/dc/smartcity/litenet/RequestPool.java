package com.dc.smartcity.litenet;

import com.dc.smartcity.litenet.response.LiteRequest;

public class RequestPool {
	/**
	 * 首页接口
	 * 
	 * @param ids
	 *            参数
	 * @param listener
	 *            监听
	 */
	public static LiteRequest GetHomePage(String ids) {
		LiteRequest re = new LiteRequest("CW0101");
		re.body.put("siteId", ids);
		return re;
	}

	/**
	 * 获取登录验证码
	 * 
	 * @param mobile
	 *            手机号码
	 * @param listener
	 *            监听器
	 */
	public static LiteRequest getRegistVerifyCode(String mobile) {
		LiteRequest re = new LiteRequest("CW0603");
		re.body.put("MOBILENUM", mobile);
		re.body.put("BUSINESSTYPE", "03");
		return re;
	}
}
