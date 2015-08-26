package com.dc.smartcity.bean.askwg;

public class WeiguanBean {

	public String userName;
	public String citizenComment;
	public String createTime;

	// 01政府回复，02其他回复
	public String commentId;
	// 用户头像
	public String userPhotoUrl;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCitizenComment(String citizenComment) {
		this.citizenComment = citizenComment;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public void setUserPhotoUrl(String userPhotoUrl) {
		this.userPhotoUrl = userPhotoUrl;
	}

}
