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

import com.facade.UserFacade;
import com.model.User;
import com.thoughtworks.xstream.XStream;

public class UsuarioResource {

	private static UserFacade userFacade = new UserFacade();

	/**
	 * Cria um arquivo XML de acordo com o id do usu�rio passado
	 * @param idUsuario
	 */
	public static void serializaUsuario(int idUsuario) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("usuario.xml"), "UTF-8"));
			xstream.toXML(userFacade.findUsuario(idUsuario), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de todos os usu�rios
	 */
	public static void serializaListaDeUsuarios() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("usuarios.xml"), "UTF-8"));
			xstream.toXML(userFacade.listAll(), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto User de acordo com o arquivo usuario.xml que existe na raiz do diret�rio
	 * @return User
	 */
	public static User deserializaUsuario() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("usuario.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		
		return (User) xstream.fromXML(input);
	}

	/**
	 * Cria uma lista de objetos User de acordo com o arquivo usuarios.xml que existe na raiz do diret�rio
	 * @return List<User>
	 */
	public static List<User> deserializaListaDeUsuarios() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream("usuarios.xml"), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (List<User>) xstream.fromXML(input);
	}
}
