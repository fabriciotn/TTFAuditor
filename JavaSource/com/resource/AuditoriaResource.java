package com.resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.facade.AuditoriaFacade;
import com.model.Auditoria;
import com.model.User;
import com.thoughtworks.xstream.XStream;

public class AuditoriaResource {
	
	private static AuditoriaFacade auditoriaFacade = new AuditoriaFacade();
	
	/**
	 * Cria um arquivo XML de acordo com o id da auditoria passada
	 * @param idAuditoria
	 */
	public static void serializaAuditoria(int idAuditoria) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("auditoria.xml"), "UTF-8"));
			xstream.toXML(auditoriaFacade.findAuditoria(idAuditoria), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de auditorias Off-line
	 */
	public static void serializaListaDeAuditoriasOff() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("auditorias.xml"), "UTF-8"));
			xstream.toXML(auditoriaFacade.findAuditoriasOff(), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto Auditoria de acordo com o arquivo auditoria.xml que existe na raiz do diretório
	 * @return Auditoria
	 */
	public static Auditoria deserializaAuditoria() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("auditoria.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		
		return (Auditoria) xstream.fromXML(input);
	}

	/**
	 * Cria uma lista de objetos Auditoria de acordo com o arquivo auditorias.xml que existe na raiz do diretório
	 * @return List<Auditoria>
	 */
	public static List<Auditoria> deserializaListaDeAuditorias() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("auditorias.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (List<Auditoria>) xstream.fromXML(input);
	}
}
