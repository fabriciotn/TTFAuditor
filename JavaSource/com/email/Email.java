package com.email;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class Email {
	
	//Configuração da conta de envio
	private final String username = "recuperasenhas@ttfinformatica.com.br";
	private final String password = "TTFAuditorADM";
	private final String fromAddress = "recuperasenhas@ttfinformatica.com.br";
	private final String fromName = "TTF Auditor - Recuperação de senha";
	private final String host = "email-ssl.com.br";//"smtp.ttfinformatica.com.br";
	private final String port = "465";
	
	
	/**
	 * Método para envio de email.
	 * @param assunto - Assunto do e-mail a ser enviado
	 * @param textoDaMensagem - Texto do e-mail a ser enviado
	 * @param addressList - Lista dos endereços de destino
	 * @return Retorna true se a mensagem for enviada com sucesso
	 */
	public boolean enviaEmail(String assunto, String textoDaMensagem, ArrayList<String> addressList){
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		MailSSLSocketFactory sf = null;
		try {
			sf = new MailSSLSocketFactory();
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		}
		sf.setTrustAllHosts(true);
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", sf);
		props.put("mail.smtp.socketFactory.class", sf);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			session.setDebug(true);
			message.setFrom(new InternetAddress(fromAddress, fromName));

			for(String email : addressList){
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.toString()));
			}
			
			message.setSubject(assunto);
			message.setContent(textoDaMensagem, "text/html");
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}