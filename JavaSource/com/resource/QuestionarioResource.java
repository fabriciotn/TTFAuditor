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

public class QuestionarioResource {

	private static QuestionarioFacade questionarioFacade = new QuestionarioFacade();

	/**
	 * Cria um arquivo XML de acordo com o id da questionario passada
	 * 
	 * @param idQuestionario
	 */
	public static void serializaQuestionario(int idQuestionario) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("questionario.xml"), "UTF-8"));
			xstream.toXML(questionarioFacade.findQuestionario(idQuestionario), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de questionarios
	 */
	public static void serializaListaDeQuestionarios() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("questionarios.xml"), "UTF-8"));
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
	public static Questionario deserializaQuestionario() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("questionario.xml"), "UTF-8"));
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
	public static List<Questionario> deserializaListaDeQuestionarios() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("questionarios.xml"), "UTF-8"));
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
