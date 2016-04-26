package com.webservices;

import javax.xml.ws.Endpoint;

public class ExecutaWS {

	public static void main(String[] args) {
		executa();
	}
	
	public static void executa(){
		AuditoriaServerWS implementacaoWS = new AuditoriaServerWS();
        String URL = "http://localhost:8080/auditoriaws";

        System.out.println("AuditoriaWS rodando: " + URL + "?wsdl");

        //associando URL com implementacao
        Endpoint.publish(URL, implementacaoWS);
	}
}
