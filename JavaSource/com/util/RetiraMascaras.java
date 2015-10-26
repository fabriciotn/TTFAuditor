package com.util;

public class RetiraMascaras {
	
	public static String retirar(String campo){
		String semMascara;
		semMascara = campo.replace("(", "");
		semMascara = semMascara.replace(")", "");
		semMascara = semMascara.replace("-", "");
		semMascara = semMascara.replace("/", "");
		semMascara = semMascara.replace(".", "");
		
		return semMascara;
	}
}
