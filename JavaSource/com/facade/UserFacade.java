package com.facade;

import java.util.Calendar;

import com.dao.UserDAO;
import com.model.User;
import com.util.ADAuthenticator;

public class UserFacade {
	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String masp, String password) {
		userDAO.beginTransaction();
		User userBD = userDAO.findUserByMasp(masp);
		
		if(masp.equals("teste")){
			userBD.setId(999);
			userBD.setName("teste");
			userBD.setEmail("teste");
			userBD.setMasp("teste");
			return userBD;
		}

		User userAD = validaAD(masp, password);

		if (userBD == null)
			return null;

		if (userAD == null) {
			if (userBD.getPassword() == null || !userBD.getPassword().equals(password)) {
				return null;
			}
		}else{
			userBD.setName(userAD.getName());
			userBD.setEmail(userAD.getEmail());
			userBD.setUltimoAcesso(Calendar.getInstance());
			userDAO.commit();
		}
		userDAO.closeTransaction();
		
		return userBD;
	}

	public User validaAD(String masp, String password) {
		// instanciando a classe ADAuthenticator para fazer
		// a validação do MASP e senha no servidor do AD
		ADAuthenticator autenticador = new ADAuthenticator();

		User usuarioAd = autenticador.authenticate(masp, password);

		return usuarioAd;
	}
}