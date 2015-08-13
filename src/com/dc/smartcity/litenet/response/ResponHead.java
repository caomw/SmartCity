package com.dc.smartcity.litenet.response;

public class ResponHead {

	public static final String CODE_SUCCES = "000000";
	private String rtnMsg;
	public String getRtnMsg() {
		return rtnMsg;
	}
	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}
	public String getRtnCode() {
		return rtnCode;
	}
	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}
	private String rtnCode;
}
