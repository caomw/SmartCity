package com.dc.smartcity.litenet.interf;
/**
 * 处理Error和取消业务请求 
 * @author szsm
 *
 */
public abstract class RequestProxy implements IResPonseListener{

	
	@Override
	public void onError(String code, String msg) {
		
	}

	@Override
	public void onCancel(String msg) {
		
	}

}
