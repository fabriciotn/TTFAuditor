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

import com.dao.RespostaDAO;
import com.facade.RespostaFacade;
import com.model.Resposta;
import com.thoughtworks.xstream.XStream;

public class RespostaResource {
	
	private String caminho;

	public RespostaResource(String caminho) {
		this.caminho = caminho;
	}
	
	private RespostaFacade respostaFacade = new RespostaFacade();

	/**
	 * Cria um arquivo XML de acordo com o id da resposta passada
	 * 
	 * @param idResposta
	 */
	public void serializaResposta(Resposta resposta) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "resposta.xml"), "UTF-8"));
			xstream.toXML(resposta, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de respostas
	 */
	public void serializaListaDeRespostas(int auditoria_id) {
		List<Resposta> respostas = respostaFacade.listaRespostasPorIdDaAuditoria(auditoria_id);
		
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "respostas.xml"), "UTF-8"));
			xstream.toXML(respostas, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto Resposta de acordo com o arquivo resposta.xml que existe na raiz do diretório
	 * @return Resposta
	 */
	public Resposta deserializaResposta() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "resposta.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		return (Resposta) xstream.fromXML(input);
	}

	/**
	 * Cria uma lista de objetos Resposta de acordo com o arquivo respostas.xml que existe na raiz do
	 * diretório
	 * 
	 * @return List<Resposta>
	 */
	public List<Resposta> deserializaListaDeRespostas() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "respostas.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (List<Resposta>) xstream.fromXML(input);
	}
}
