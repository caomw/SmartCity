package com.dc.smartcity.bean;

import java.util.List;

/**
 * 首页数据
 * @author szsm
 *
 */
public class HomeObj {

	public List<String> bannerPic;
	public List<String> getBannerPic() {
		return bannerPic;
	}
	public void setBannerPic(List<String> bannerPic) {
		this.bannerPic = bannerPic;
	}
	public List<DCMenuItem> getRecommendServiceList() {
		return recommendServiceList;
	}
	public void setRecommendServiceList(List<DCMenuItem> recommendServiceList) {
		this.recommendServiceList = recommendServiceList;
	}
	public List<ScenceItem> getSceneColumnList() {
		return sceneColumnList;
	}
	public void setSceneColumnList(List<ScenceItem> sceneColumnList) {
		this.sceneColumnList = sceneColumnList;
	}
	/**
	 * 配置服务
	 */
	public List<DCMenuItem> recommendServiceList;
	/**
	 * 场景服务
	 */
	public List<ScenceItem> sceneColumnList;
}
