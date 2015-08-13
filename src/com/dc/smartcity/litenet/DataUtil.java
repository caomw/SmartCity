package com.dc.smartcity.litenet;


import org.apache.commons.codec.binary.Base64;

import android.util.Log;

import com.alibaba.fastjson.JSON;





public class DataUtil {

	
   public static String signRequest(String appid,Object src,String appkey)throws Exception{
	   
	    String srcText = JSON.toJSONString(src);
	    System.out.println();
	    System.out.println("appid + srcText === "+appid + srcText);
		// 对报文进行BASE64编码，避免中文处理问题
		String base64Text = new String(Base64.encodeBase64((appid + srcText)
				.getBytes("utf-8"), false));
		// MD5摘要，生成固定长度字符串用于加密
		String destText = MD5Util.md5Digest(base64Text);
		AlgorithmData data = new AlgorithmData();
		data.setDataMing(destText);
		data.setKey(appkey);
		// 3DES加密
		Algorithm3DES.encryptMode(data);
		
		Log.e("DataUtil", "encode: "+data.getDataMi());
		return data.getDataMi();
   }
	
}