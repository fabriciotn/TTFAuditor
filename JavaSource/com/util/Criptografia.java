package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

	

	public static String criptografa(String senha){
		MessageDigest md;
		StringBuffer sb = null;
		
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(senha.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		return sb.toString();
	}
}
