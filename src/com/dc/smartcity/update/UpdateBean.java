package com.dc.smartcity.update;

public class UpdateBean {
	/*
	 * "versionCode": 1, "versionName": "1.0.0", "versionDetail":
	 * "the first version", "isUpdate": false downloadUrl
	 */
	public String versionCode;
	public String versionName;
	public String versionDetail;
	public boolean isUpdate;
	public String downLoadUrl;

	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public void setVersionDetail(String versionDetail) {
		this.versionDetail = versionDetail;
	}

	public void setIsUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}



	

}
