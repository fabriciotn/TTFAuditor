package com.facade;

import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import com.dao.UserDAO;
import com.mb.MessagesView;
import com.model.Pendencia;
import com.model.User;
import com.util.ADAuthenticator;
import com.util.Criptografia;

public class UserFacade {
	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String login, String password) {
		password = Criptografia.criptografa(password);
		
		userDAO.beginTransaction();
		User user = userDAO.findUserByLogin(login);

		if (user != null){
			if (user.getPassword() == null) {
				return null;
			}
			if(user.getPassword().equals(password)){
				user.setUltimoAcesso(Calendar.getInstance());
				userDAO.commit();
			}else{
				return null;
			}
		}else{
			return null;
		}
		
		userDAO.closeTransaction();
		
		return user;
	}

	public User validaAD(String login, String password) {
		// instanciando a classe ADAuthenticator para fazer
		// a validação do Login e senha no servidor do AD
		ADAuthenticator autenticador = new ADAuthenticator();

		User usuarioAd = autenticador.authenticate(login, password);

		return usuarioAd;
	}
	
	public List<User> listAll() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllAsc();
		userDAO.closeTransaction();
		return result;
	}
}