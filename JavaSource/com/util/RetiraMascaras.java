package com.util;

/**
 * Classe para retirar as m�scaras dos campos de telefones e CEP's
 * @author TTF Inform�tica
 *
 */
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
