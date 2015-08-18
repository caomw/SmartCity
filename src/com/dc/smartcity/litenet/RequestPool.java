package com.dc.smartcity.litenet;

import com.dc.smartcity.litenet.response.LiteRequest;

public class RequestPool {
	
	public static String cityCode = "320581";
	/**
	 * 首页接口
	 */
	public static LiteRequest GetHomePage() {
		LiteRequest re = new LiteRequest("CW0101");
		re.body.put("siteId", cityCode);
		return re;
	}
	/**
	 * 更多接口：加载顶部目录及第一个子列表
	 */
	public static LiteRequest GetMoreService() {
		LiteRequest re = new LiteRequest("CW0103");
		re.body.put("siteId", cityCode);
		return re;
	}

	/**
	 * 获取登录验证码
	 * 
	 * @param mobile
	 *            手机号码
	 */
	public static LiteRequest getRegistVerifyCode(String mobile) {
		LiteRequest re = new LiteRequest("cs_portal/service/CW0603");
		re.body.put("MOBILENUM", mobile);
		re.body.put("BUSINESSTYPE", "03");
		return re;
	}
	/**
	 * 注册
	 * @param mobile
	 * @return
	 */
	public static LiteRequest registQuest(String login, String pass,String mobile,String verifyCode) {
		LiteRequest re = new LiteRequest("CW0102");
		re.body.put("LOGIN", login);
		re.body.put("PASSWORD", pass);
		re.body.put("SITEID", cityCode);
		re.body.put("MOBILENUM", mobile);
		re.body.put("CODE", verifyCode);
		re.body.put("PWDSTRENGTH", "1");//密码强度（1或2）
		return re;
	}
	
	/**
	 * 登陆接口
	 * @param username
	 * 				用户名
	 * @param password
	 * 				密码
	 * @return
	 */
	public static LiteRequest requestLogin(String username,String password){
		LiteRequest re = new LiteRequest(Config.LOGIN_URL);
		re.body.put("username", username);
		re.body.put("password", password);
		return re;
	}
}
