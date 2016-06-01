package resource;

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

	public static void serializaUsuario(){
		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("cliente.xml"), "UTF-8"));
			xstream.toXML(userFacade.listAll(), output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static User deserializaUsuario(String arquivoXml) {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(arquivoXml), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		XStream xstream = new XStream();
		xstream.setMode(XStream.ID_REFERENCES);
		return (User) xstream.fromXML(input);
	}

	public static List<User> deserializaListaDeUsuarios(String arquivoXml) {
		BufferedReader input = null;

		try {
			input = new BufferedReader(new InputStreamReader(
					new FileInputStream(arquivoXml), "UTF-8"));
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
