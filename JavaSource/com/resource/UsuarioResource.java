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

/**
 * Classe responsável pelos métodos por serializar e deserializar Usuários
 * @author TTF Informática
 *
 */
public class UsuarioResource {

	private String caminho;

	public UsuarioResource(String caminho) {
		this.caminho = caminho;
	}
	
	private UserFacade userFacade = new UserFacade();

	/**
	 * Cria um arquivo XML de acordo com o id do usuário passado
	 * @param idUsuario
	 */
	public void serializaUsuario(User usuario) {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "usuario.xml"), "UTF-8"));
			xstream.toXML(usuario, output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um arquivo XML com a lista de todos os usuários
	 */
	public void serializaListaDeUsuarios() {
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);

		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(caminho + "usuarios.xml"), "UTF-8"));
			xstream.toXML(userFacade.listAll(), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Cria um objeto User de acordo com o arquivo usuario.xml que existe na raiz do diretório
	 * @return User
	 */
	public User deserializaUsuario() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "usuario.xml"), "UTF-8"));
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
	 * Cria uma lista de objetos User de acordo com o arquivo usuarios.xml que existe na raiz do diretório
	 * @return List<User>
	 */
	public List<User> deserializaListaDeUsuarios() {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(new FileInputStream(caminho + "usuarios.xml"), "UTF-8"));
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
