package com.email;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.model.Pendencia;
import com.model.Prioridade;
import com.model.User;

public class PendenciaCadastrada {

	public static void enviaEmail(Pendencia pendencia) {
		Email email = new Email();
		
		String assunto = "Nova pendência cadastrada - Sistema de Gerenciamento de Tarefas";
		String textoDaMensagem;
		ArrayList<String> addressList = new ArrayList<String>();
		
		String setor = "";
		String categoria = "";
		String prioridade = "";
		String descricao = "";
		String usuario = "";
		
		if(pendencia.getSetor() != null)
			setor = pendencia.getSetor().getNome();
			
		if(pendencia.getCategoria() != null)
			categoria = pendencia.getCategoria().getLabel();
		
		if(pendencia.getPrioridade() != null)
			prioridade = pendencia.getPrioridade().getLabel();
		
		if(pendencia.getDescricao() != null)
			descricao = pendencia.getDescricao();
		
		if(pendencia.getUsuario() != null)
			usuario = pendencia.getUsuario().getName();
		
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
					"<img src=\"http://10.14.124.101:8080/HemoProjectApp/images/logoHemominasEmail.png\">" +
					"<br>" +
					"<h3> " + assunto + "</h3>" +
					"<p>Olá, <br>Uma nova pendência foi cadastrada pelo usuário: <b>" + usuario + "</b></p>" +
					"Seguem os dados da pendência:" +
					"<ul>" +
						"<li><b>ID:</b> " + pendencia.getId() + "</li>" +
						"<li><b>Setor:</b> " + setor + "</li>" +
						"<li><b>Categoria:</b> " + categoria + "</li>" +
						"<li><b>Prioridade:</b> " + prioridade + "</li>" +
						"<li><b>Descrição:</b> " + descricao + "</li>" +
					"</ul>" +
					"<br>" +
					"<p>" +
						"Atenciosamente,<br> <b>Equipe GTC</b>" +
					"</p>" +
					"<small>Este relatório foi gerado automaticamente pelo sistema.</small>" +
				"</body>" +
				"</html>";
		
		//Envia para o usuário que cadastrou
		if(pendencia.getUsuario().getEmail() != null)
			addressList.add(pendencia.getUsuario().getEmail());
		
		//Envia para todos os responsáveis pelo setor
		List<User> usuariosResponsaveisPeloSetor = new ArrayList<User>();
		usuariosResponsaveisPeloSetor = pendencia.getSetor().getUsuarios();
		for (User user : usuariosResponsaveisPeloSetor) {
			addressList.add(user.getEmail());
		}
		
		//Envia para a SOFIS, Fernando e Maildes se a prioridade for GRAVE
		if(pendencia.getPrioridade() == Prioridade.GRAVE){
			addressList.add("antonio.mendes@sofis.com.br");
			addressList.add("fernando.basques@hemominas.mg.gov.br");
		}

		
		email.enviaEmail(assunto, textoDaMensagem, addressList);
	}
}
