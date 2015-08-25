package com.dc.smartcity.litenet;

import com.dc.smartcity.bean.user.ModifyUserBean;
import com.dc.smartcity.litenet.response.LiteRequest;

public class RequestPool {

	/**
	 * 首页接口
	 */
	public static LiteRequest GetHomePage() {
		LiteRequest re = new LiteRequest("CW0101");
		re.body.put("siteId", Config.cityCode);
		return re;
	}

	/**
	 * 更多接口：加载顶部目录及第一个子列表
	 */
	public static LiteRequest GetMoreService() {
		LiteRequest re = new LiteRequest("CW0103");
		re.body.put("siteId", Config.cityCode);
		return re;
	}

	/**
	 * 更多接口：加载顶子列表
	 */
	public static LiteRequest GetMoreServiceItem(String columnId,
			String columnName) {
		LiteRequest re = new LiteRequest("CW0103");
		re.body.put("siteId", Config.cityCode);
		re.body.put("columnId", columnId);
		re.body.put("columnName", columnName);
		return re;
	}

	// /**
	// * 获取登录验证码
	// *
	// * @param mobile 手机号码
	// */
	// public static LiteRequest getRegistVerifyCode(String mobile) {
	// LiteRequest re = new LiteRequest("CW0104");
	// re.body.put("MOBILENUM", mobile);
	// re.body.put("BUSINESSTYPE", "03");
	// return re;
	// }

	/**
	 * 获取登录验证码 01手机绑定；02邮箱绑定；03注册绑定手机；04找回密码；05市民卡注册绑定；06市民卡实名认证
	 *
	 * @param mobile
	 *            手机号码
	 */
	public static LiteRequest getVerifyCode(String mobile, String code) {
		LiteRequest re = new LiteRequest("CW0104");
		re.body.put("MOBILENUM", mobile);
		re.body.put("BUSINESSTYPE", code);
		return re;
	}

	/**
	 * 注册
	 *
	 * @param mobile
	 * @return
	 */
	public static LiteRequest registQuest(String login, String pass,
			String mobile, String verifyCode) {
		LiteRequest re = new LiteRequest("center_userservice/service/CW0102");
		re.body.put("LOGIN", login);
		re.body.put("PASSWORD", pass);
		re.body.put("SITEID", Config.cityCode);
		re.body.put("MOBILENUM", mobile);
		re.body.put("CODE", verifyCode);
		re.body.put("PWDSTRENGTH", "1");// 密码强度（1或2）
		return re;
	}

	/**
	 * 获取服务列表
	 *
	 * @param columnId
	 * @return
	 */
	public static LiteRequest getServiceList(String columnId) {
		LiteRequest re = new LiteRequest("cs_phoneInterface/service/CW0102");
		re.body.put("columnId", columnId);
		re.body.put("siteId", Config.cityCode);
		return re;
	}
	/**
	 * 修改用户密码
	 *
	 * @param columnId
	 * @return
	 */
	public static LiteRequest changePass(String opass,String npass) {
		LiteRequest re = new LiteRequest("center_userservice/CW0108");
		re.body.put("PWDSTRENGTH", "02");
		re.body.put("NEWPWD", npass);
		re.body.put("OLDPWD", opass);
		return re;
	}

	/**
	 * 登陆接口
	 *
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public static LiteRequest requestLogin(String username, String password) {
		LiteRequest re = new LiteRequest(Config.LOGIN_URL);
		re.body.put("username", username);
		re.body.put("password", password);
		return re;
	}

	/**
	 * 查询用户信息
	 *
	 * @return
	 */
	public static LiteRequest requestUserInfo() {
		LiteRequest re = new LiteRequest("cs_portal/service/CW0201");
		return re;
	}

	/**
	 * 搜索服务
	 *
	 * @return
	 */
	public static LiteRequest searchService(String keyword) {
		LiteRequest re = new LiteRequest("cs_phoneInterface/service/CW0106");
		re.body.put("keyword", keyword);
		re.version = Config.version1;
		return re;
	}

	/**
	 * 实名认证
	 *
	 * @return
	 */
	public static LiteRequest requestAuth(String name, String idcardCode,
			String fpic, String bpic, String mobile) {
		LiteRequest re = new LiteRequest("sso/service/CW0606");
		re.body.put("NAME", name);
		re.body.put("IDCARDCODE", idcardCode);
		re.body.put("FPICTURE", fpic);
		re.body.put("BPICTURE", bpic);
		re.body.put("MOBILENUM", mobile);
		re.body.put("CODE", "123456");
		return re;
	}

	/**
	 * 问答-全部
	 *
	 * @return
	 */
	public static LiteRequest requestQannAns(int pageNo) {
		LiteRequest re = new LiteRequest("cs_mo/service/CW1701");
		re.body.put("PAGE_INDEX", String.valueOf(pageNo));
		re.body.put("PAGE_COUNT", "8");
		return re;
	}

	/**
	 * 问答-我的
	 *
	 * @return
	 */
	public static LiteRequest requestMQannAns(int pageNo) {
		LiteRequest re = new LiteRequest("cs_mo/service/CW1703");
		re.body.put("PAGE_INDEX", String.valueOf(pageNo));
		re.body.put("PAGE_COUNT", "8");
		return re;
	}

	/**
	 * 修改用户信息
	 *
	 * @return
	 */
	public static LiteRequest requestModifyUserInfo(ModifyUserBean bean) {
		LiteRequest re = new LiteRequest("cs_portal/service/CW0202");
		re.body.put("BIRTHDAY", bean.birth);
		re.body.put("MARRY", bean.marry);
		re.body.put("NAME", bean.name);
		re.body.put("RESIDENCE", bean.residence);
		re.body.put("SEX", bean.sex);
		return re;
	}
	/**
	 * 发布微观
	 *
	 * @return
	 */
	public static LiteRequest submitWeiGuan(String title,String content,String isPublic,String loc) {
		LiteRequest re = new LiteRequest("cs_portal/service/CW0202");
		re.body.put("LOCATION", loc);
		re.body.put("IS_PUBLIC", isPublic);
		re.body.put("CONTENT", content);
		re.body.put("TITLE", title);
		return re;
	}
}
