package com.dc.smartcity.bean;

public class DCMenuItem {
	/**
	 * 服务名称
	 */
	private String serviceName;
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServicePicUrl() {
		return servicePicUrl;
	}
	public void setServicePicUrl(String servicePicUrl) {
		this.servicePicUrl = servicePicUrl;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * 服务图标
	 */
	private String servicePicUrl;
	/**
	 * 服务图标
	 */
	private String serviceUrl;
	/**
	 * 0普通用户访问、1需要登陆、2需要实名认证
	 */
	private String level;
}
