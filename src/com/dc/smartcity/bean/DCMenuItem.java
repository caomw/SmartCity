package com.dc.smartcity.bean;

public class DCMenuItem {
	/**
	 * 服务名称
	 */
	public String serviceName;
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public void setServicePicUrl(String servicePicUrl) {
		this.servicePicUrl = servicePicUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 服务图标
	 */
	public String servicePicUrl;
	/**
	 * 服务图标
	 */
	public String serviceUrl;
	/**
	 * 0普通用户访问、1需要登陆、2需要实名认证
	 */
	public String level;
}
