package com.dc.smartcity.bean.user;

/**
 * 用户基础信息
 * 
 * @author check_000
 *
 */
public class UserBaseBean {
	/**
	 * "login": "anshiwei001", "name": "anshiwei001", "level": "01",
	 * "headphotourl":
	 * "http://test.zjgsmwy.com/image/get/system/avatar/947482aeffce4dadba693d16facab13e.jpg"
	 * , "state": "1", "siteid": "320100", "channelid": null, "sex": "02",
	 * "birthday": null, "marry": "", "registertime": 1403801712000,
	 * "modifytime": 1439800627000, "lastlogintime": 1439925149000,
	 * "isfirstlogin": null
	 */
	public String userid;
	public String login;
	public String name;
	public String level;
	public String headphotourl;
	public String sex;
	public String birthday;
	public String lastlogintime;

	public void setLastlogintime(String lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setHeadphotourl(String headphotourl) {
		this.headphotourl = headphotourl;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	
}
