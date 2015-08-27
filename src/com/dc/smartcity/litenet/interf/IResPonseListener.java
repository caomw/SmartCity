package com.dc.smartcity.litenet.interf;
/**
 * 请求返回接口
 * @author szsm
 *
 */
public interface IResPonseListener {

	/**
	 * 业务请求成功
	 * @param success
	 * @param msg
	 * @param result
	 */
	void onSuccess(String msg, String result);
	/**
	 * 业务处理失败
	 * @param code
	 * @param msg
	 */
	void onError(String code, String msg);
	/**
	 * 取消
	 * @param msg
	 */
	void onCancel(String msg);
}
