package com.dc.smartcity.litenet;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    // 加密
    public static String encrypt(String sSrc, String sKey) {
    	try{
	        if (sKey==null || "".equals(sKey)) {
	            return null;
	        }
	        byte[] raw = sKey.getBytes("UTF-8");
	        if (raw.length!=16) {
	            return null;
	        }
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
	        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes("UTF-8"));
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	        
	        //自动填充为0
	        byte[] sSrcBytes = sSrc.getBytes("UTF-8");
            int sSrcBytesLength = sSrcBytes.length;
            if (sSrcBytesLength%16 != 0) {
            	sSrcBytesLength = sSrcBytesLength + (16 - (sSrcBytesLength%16));
            }
            byte[] plainText = new byte[sSrcBytesLength];
            for(int i=0;i<plainText.length;i++){
            	plainText[i] = 0;
            }
            System.arraycopy(sSrcBytes, 0, plainText, 0, sSrcBytes.length);
	        byte[] encrypted = cipher.doFinal(plainText);
//	        BASE64Encoder b64enc = new BASE64Encoder();
//	        String encryptedtext = b64enc.encode(encrypted);
//	        System.out.println(encryptedtext);
	        return byte2hex(encrypted);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }

    // 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
	        if (sKey==null || "".equals(sKey)) {
	            return null;
	        }
	        byte[] raw = sKey.getBytes("UTF-8");
	        if (raw.length!=16) {
	            return null;
	        }
	        if(sSrc.length()%16!=0){
	        	return null;
	        }
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] decrypted = hex2byte(sSrc);
            byte[] original = cipher.doFinal(decrypted);
            String originalString = new String(original, "UTF-8");
            return originalString;
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }

    public static byte[] hex2byte(String hex) {
        if (hex == null) {
            return null;
        }
        int l = hex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
    
    public static String sha1(String s){
		MessageDigest md = null;
		StringBuffer sb = new StringBuffer();
		try {
			md = MessageDigest.getInstance("SHA-1");
			md.update(s.getBytes("UTF-8"));
			byte[] result = md.digest();
			
	        for (byte b : result) {
	            int i = b & 0xff;
	            String st = Integer.toHexString(i);
	            if (st.length()<2) {
	                sb.append(0);
	            }
	            sb.append(st);
	        }
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
        if(sb.length()==0) return null;
        return sb.toString();
	}
    
    
    public static String md5(String s){
		MessageDigest md = null;
		StringBuffer sb = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(s.getBytes("UTF-8"));
			byte[] result = md.digest();
			
	        for (byte b : result) {
	            int i = b & 0xff;
	            String st = Integer.toHexString(i);
	            if (st.length()<2) {
	                sb.append(0);
	            }
	            sb.append(st);
	        }
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
        if(sb.length()==0) return null;
        return sb.toString();
	}
    
    public static String username(String un){
    	return AES.encrypt(un, "123456789ABCDEFG");
    }
    public static String password(String pw){
    	return AES.encrypt(sha1(pw), "123456789ABCDEFG");
    }

    public static void main(String[] args) throws Exception {    	
    	System.out.println( username("fgtest") );
    	System.out.println( password("22222") );
    }
}
