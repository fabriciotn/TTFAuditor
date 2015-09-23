package com.testes;

import java.util.ArrayList;

import com.email.Email;

public class TesteDeEnvioDeEmail {

	public static void main(String[] args) {
		Email email = new Email();
		
		String assunto = "Teste de envio de e-mail - fabricio";
		String textoDaMensagem;
		ArrayList<String> addressList = new ArrayList<String>();
		
		textoDaMensagem = "<!DOCTYPE html>" +
							"<html>" +
							"<head>" +
							"<meta charset=\"ISO-8859-1\">" +
							"<title></title>" +
							"<style>" +
							"body {" +
								"font-family: verdana;" +
								"font-size: 0.9em;" +
							"}" +
							"</style>" +
							"</head>" +
							"<body>" +
								"<img src=\"http://localhost:8080/HemoProjectApp/images/logoHemominasEmail.png\">" +
								"<br>" +
								"<h2>Nova pendência cadastrada</h2>" +
								"<p>" +
									"Olá, <br>Uma nova pendência foi cadastrada pelo usuário: XXXX" +
								"</p>" +
								"Seguem os dados da pendência:" +
								"<ul>" +
									"<li>ID: XX</li>" +
									"<li>Setor: XXX</li>" +
									"<li>Categoria: XXX</li>" +
									"<li>Prioridade: XXX</li>" +
									"<li>Descrição: XXXXXXXXXXXXXX</li>" +
								"</ul>" +
								"<br>" +
								"<p>" +
									"Atenciosamente,<br> <b>Equipe GTC</b>" +
								"</p>" +
								"<small>Este relatório foi gerado automaticamente pelo sistema.</small>" +
							"</body>" +
							"</html>";
		
		addressList.add("fabricio.teixeira@hemominas.mg.gov.br");
		addressList.add("fabriciotn@yahoo.com.br");
		addressList.add("fabricio.hemominas@gmail.com");
		addressList.add("vanessa.cruz@hemominas.mg.gov.br");
		
		//enviaEmail está como protected para forçar a sempre enviar e-mail pelas classes 
		//PendenciaCadastrada, PendenciaFechada e PendenciaAlterada
		email.enviaEmail(assunto, textoDaMensagem, addressList);
	}
}
