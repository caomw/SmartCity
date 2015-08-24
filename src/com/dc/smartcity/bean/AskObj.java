package com.dc.smartcity.bean;

import java.io.Serializable;

/**
 * Created by vincent on 2015/8/9.
 */
public class AskObj implements Serializable{

	//应该是事务id
    public String observeId;
    public String isPublic;
    public String happenTime;
    public String happenTimeDate;
    public String createTimeDate;
    public String location;
    public String content;
    public String title;
    public String contactEmail;
    public String contactMobile;
    public String userName;
    public String photoUrl;
    public String status;
    public void setObserveId(String observeId) {
		this.observeId = observeId;
	}
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}
	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}
	public void setHappenTimeDate(String happenTimeDate) {
		this.happenTimeDate = happenTimeDate;
	}
	public void setCreateTimeDate(String createTimeDate) {
		this.createTimeDate = createTimeDate;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
