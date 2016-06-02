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

import com.facade.EstabelecimentoFacade;
import com.model.Estabelecimento;
import com.thoughtworks.xstream.XStream;

public class EstabelecimentoResource {

	private static EstabelecimentoFacade estabelecimentoFacade = new EstabelecimentoFacade();
	
	/**
	 * Cria um arquivo XML de acordo com o id da estabelecimento passada
	 * @param idEstabelecimento
	 */
	public static void serializaEstabelecimento(int idEstabelecimento) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("estabelecimento.xml"), "UTF-8"));
			xstream.toXML(estabelecimentoFacade.findEstabelecimento(idEstabelecimento), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de estabelecimentos
	 */
	public static void serializaListaDeEstabelecimentos() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("estabelecimentos.xml"), "UTF-8"));
			xstream.toXML(estabelecimentoFacade.listAll(), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto Estabelecimento de acordo com o arquivo estabelecimento.xml que existe na raiz do diretório
	 * @return Estabelecimento
	 */
	public static Estabelecimento deserializaEstabelecimento() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("estabelecimento.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		
		return (Estabelecimento) xstream.fromXML(input);
	}

	/**
	 * Cria uma lista de objetos Estabelecimento de acordo com o arquivo estabelecimentos.xml que existe na raiz do diretório
	 * @return List<Estabelecimento>
	 */
	public static List<Estabelecimento> deserializaListaDeEstabelecimentos() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("estabelecimentos.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (List<Estabelecimento>) xstream.fromXML(input);
	}
}
