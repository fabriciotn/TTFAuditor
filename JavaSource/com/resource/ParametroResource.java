package com.resource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.facade.ParametrosFacade;
import com.model.Parametros;
import com.thoughtworks.xstream.XStream;

/**
 * Classe responsável pelos métodos por serializar e deserializar Parâmetros
 * @author TTF Informática
 *
 */
public class ParametroResource {

	private String caminho;

	public ParametroResource(String caminho) {
		this.caminho = caminho;
	}
	
	private ParametrosFacade parametrosFacade = new ParametrosFacade();

	/**
	 * Cria um arquivo XML dos parametros - Registro 1 no banco.
	 */
	public void serializaParametro() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "parametros.xml"), "UTF-8"));
			xstream.toXML(parametrosFacade.findParametros(1), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto Parametros de acordo com o arquivo parametros.xml que existe na raiz do diretório
	 * @return
	 */
	public Parametros deserializaParametro() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "parametros.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (Parametros) xstream.fromXML(input);
	}

}
