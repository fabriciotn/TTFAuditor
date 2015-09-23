package com.email;

import java.util.ArrayList;
import java.util.List;

import com.model.Iteracao;
import com.model.Prioridade;
import com.model.User;

public class PendenciaFechada {
	public static void enviaEmail(Iteracao iteracao) {
		Email email = new Email();
		
		String assunto = "Pendência " + iteracao.getPendencia().getId() + " foi encerrada - Sistema de Gerenciamento de Tarefas";
		String textoDaMensagem;
		ArrayList<String> addressList = new ArrayList<String>();
		
		String setor = "";
		String categoria = "";
		String prioridade = "";
		String descricao = "";
		String usuario = "";
		
		if(iteracao.getPendencia() != null)
			setor = iteracao.getPendencia().getSetor().getNome();
			
		if(iteracao.getCategoria() != null)
			categoria = iteracao.getCategoria().getLabel();
		
		if(iteracao.getPrioridade() != null)
			prioridade = iteracao.getPrioridade().getLabel();
		
		if(iteracao.getDescricao() != null)
			descricao = iteracao.getDescricao();
		
		if(iteracao.getUsuario() != null)
			usuario = iteracao.getUsuario().getName();
		
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
					"<p>Olá, <br>A pendência número <b>" + iteracao.getPendencia().getId() + "</b> foi encerrada!<br>" +
						"Por favor verifique se está tudo de acordo!</p>" +
					
					"Seguem os dados da pendência:" +
					"<ul>" +
						"<li><b>ID:</b> " + iteracao.getPendencia().getId() + "</li>" +
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
		if(iteracao.getPendencia().getUsuario().getEmail() != null)
			addressList.add(iteracao.getPendencia().getUsuario().getEmail());
		
		// Envia para todos os responsáveis pelo setor
		List<User> usuariosResponsaveisPeloSetor = new ArrayList<User>();
		usuariosResponsaveisPeloSetor = iteracao.getPendencia().getSetor().getUsuarios();
		for (User user : usuariosResponsaveisPeloSetor) {
			addressList.add(user.getEmail());
		}
		
		//Envia para a SOFIS, Fernando e Maildes se a prioridade for GRAVE
		if(iteracao.getPendencia().getPrioridade() == Prioridade.GRAVE){
			addressList.add("antonio.mendes@sofis.com.br");
			addressList.add("fernando.basques@hemominas.mg.gov.br");
		}

		
		email.enviaEmail(assunto, textoDaMensagem, addressList);
	}
}
