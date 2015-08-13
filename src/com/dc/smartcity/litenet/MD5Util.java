package com.dc.smartcity.litenet;

import java.security.MessageDigest;

public class MD5Util {

	public static String md5Digest(String pwd)throws Exception{
		try{
	      MessageDigest alg = MessageDigest.getInstance("MD5");
	      alg.update(pwd.getBytes("UTF-8"));
	      byte[] digest = alg.digest();
	      return byte2hex(digest);
	    } catch (Exception e) {
	      System.out.println(e.toString());
	    }return "";
	}
	
	private static String byte2hex(byte[] b)
	  {
	    String hs = "";
	    String stmp = "";
	    for (int n = 0; n < b.length; n++) {
	      stmp = Integer.toHexString(b[n] & 0xFF);
	      if (stmp.length() == 1)
	        hs = hs + "0" + stmp;
	      else
	        hs = hs + stmp;
	    }
	    return hs.toLowerCase();
	  }
	public static void main(String[] args)throws Exception{
		System.out.println(MD5Util.md5Digest("123456uiyouiutiuyiuyiuyuiyuiyiuyiyiuyuytyuytuytuy"));
	}
}
