package com.resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import com.facade.RespostaFacade;
import com.model.Resposta;
import com.thoughtworks.xstream.XStream;

/**
 * Classe responsável pelos métodos por serializar e deserializar Respostas
 * @author TTF Informática
 *
 */
public class RespostaResource {

	private RespostaFacade respostaFacade = new RespostaFacade();

	/**
	 * Cria um arquivo XML de acordo com o id da resposta passada
	 * 
	 * @param idResposta
	 */
	public void serializaResposta(String caminho, Resposta resposta) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(caminho + "resposta_" + Calendar.getInstance().getTimeInMillis() + ".xml"), "UTF-8"));
			xstream.toXML(resposta, output);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de respostas
	 */
	public void serializaListaDeRespostas(String caminho, int auditoria_id) {
		List<Resposta> respostas = respostaFacade.listaRespostasPorIdDaAuditoria(auditoria_id);

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(caminho + "respostas_" + Calendar.getInstance().getTimeInMillis() + ".xml"), "UTF-8"));
			xstream.toXML(respostas, output);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto Resposta de acordo com o arquivo resposta.xml que existe na raiz do diretório
	 * 
	 * @return Resposta
	 */
	public Resposta deserializaResposta(String caminhoCompleto) {
		BufferedReader input = null;

		try {
			input = new BufferedReader(
					new InputStreamReader(new FileInputStream(caminhoCompleto), "UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		Resposta resposta = (Resposta) xstream.fromXML(input);
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resposta;
	}

	/**
	 * Cria uma lista de objetos Resposta de acordo com o arquivo respostas.xml que existe na raiz do diretório
	 * 
	 * @return List<Resposta>
	 */
	public List<Resposta> deserializaListaDeRespostas(String caminhoCompleto) {
		BufferedReader input = null;

		try {
			input = new BufferedReader(
					new InputStreamReader(new FileInputStream(caminhoCompleto), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		
		List<Resposta> respostas = (List<Resposta>) xstream.fromXML(input);
		try {
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return respostas;
	}
}
