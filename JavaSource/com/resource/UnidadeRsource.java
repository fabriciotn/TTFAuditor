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

import com.facade.UnidadeFacade;
import com.model.Unidade;
import com.thoughtworks.xstream.XStream;

public class UnidadeRsource {

	private String caminho;

	public UnidadeRsource(String caminho) {
		this.caminho = caminho;
	}
	
	private UnidadeFacade unidadeFacade = new UnidadeFacade();

	/**
	 * Cria um arquivo XML de acordo com o id da unidade passada
	 * 
	 * @param idUnidade
	 */
	public void serializaUnidade(Unidade unidade) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "unidade.xml"), "UTF-8"));
			xstream.toXML(unidade, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de unidades
	 */
	public void serializaListaDeUnidades() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "unidades.xml"), "UTF-8"));
			xstream.toXML(unidadeFacade.listAll(), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto Unidade de acordo com o arquivo unidade.xml que existe na raiz do diretório
	 * @return Unidade
	 */
	public Unidade deserializaUnidade() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "unidade.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		return (Unidade) xstream.fromXML(input);
	}

	/**
	 * Cria uma lista de objetos Unidade de acordo com o arquivo unidades.xml que existe na raiz do
	 * diretório
	 * 
	 * @return List<Unidade>
	 */
	public List<Unidade> deserializaListaDeUnidades() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "unidades.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (List<Unidade>) xstream.fromXML(input);
	}
}
