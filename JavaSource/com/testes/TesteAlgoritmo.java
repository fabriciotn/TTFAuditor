package com.testes;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.util.Criptografia;

public class TesteAlgoritmo {
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		for (int i = 0; i < 10; i++) {
			criptografa("fabricio");
		}
	}

	private static void criptografa(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(senha.getBytes());
        
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
     
        System.out.println("Hex format : " + sb.toString());
	}
}