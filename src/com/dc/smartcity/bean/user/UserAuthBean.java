package com.dc.smartcity.bean.user;
/**
 * 授权信息
 * @author check_000
 *
 */
public class UserAuthBean {

	/**
	 * "userid": "947482aeffce4dadba693d16facab13e",
                "login": "anshiwei001",
                "password": "0998c6f617a6c45dcfa485c843b8f64d6ca1e47abd42ae532dcabc6372562330bf08a360694f480f",
                "mobilenum": "18301222402",
                "mobileisbound": "01",
                "email": "18301222402@163.com",
                "emailisbound": "01",
                "idcardcode": "",
                "spassword": null,
                "updatetime": null,
                "flag": null,
                "cardnum": null,
                "pwdstrength": "1"

	 */
	
	public String  mobilenum;
	public String  email;
	public String  pwdstrength;//1弱，2强
	public String  mobileisbound;
	public String  emailisbound;
	public String  idcardcode;
	public void setIdcardcode(String idcardcode) {
		this.idcardcode = idcardcode;
	}
	public void setEmailisbound(String emailisbound) {
		this.emailisbound = emailisbound;
	}
	public void setMobileisbound(String mobileisbound) {
		this.mobileisbound = mobileisbound;
	}
	public void setPwdstrength(String pwdstrength) {
		this.pwdstrength = pwdstrength;
	}
	public void setMobilenum(String mobilenum) {
		this.mobilenum = mobilenum;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
