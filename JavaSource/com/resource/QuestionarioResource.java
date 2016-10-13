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

import com.facade.QuestionarioFacade;
import com.model.Questionario;
import com.thoughtworks.xstream.XStream;

/**
 * Classe responsável pelos métodos por serializar e deserializar Questionários
 * @author TTF Informática
 *
 */
public class QuestionarioResource {

	private String caminho;

	public QuestionarioResource(String caminho) {
		this.caminho = caminho;
	}
	
	private QuestionarioFacade questionarioFacade = new QuestionarioFacade();

	/**
	 * Cria um arquivo XML de acordo com o id da questionario passada
	 * 
	 * @param idQuestionario
	 */
	public void serializaQuestionario(Questionario questionario) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "questionario.xml"), "UTF-8"));
			xstream.toXML(questionario, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de questionarios
	 */
	public void serializaListaDeQuestionarios() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "questionarios.xml"), "UTF-8"));
			xstream.toXML(questionarioFacade.listAll(), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto Questionario de acordo com o arquivo questionario.xml que existe na raiz do diretório
	 * @return Questionario
	 */
	public Questionario deserializaQuestionario() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "questionario.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		return (Questionario) xstream.fromXML(input);
	}

	/**
	 * Cria uma lista de objetos Questionario de acordo com o arquivo questionarios.xml que existe na raiz do
	 * diretório
	 * 
	 * @return List<Questionario>
	 */
	public List<Questionario> deserializaListaDeQuestionarios() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "questionarios.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (List<Questionario>) xstream.fromXML(input);
	}
}
