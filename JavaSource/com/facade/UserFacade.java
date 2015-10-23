package com.facade;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import com.dao.UserDAO;
import com.model.User;
import com.util.ADAuthenticator;
import com.util.Criptografia;

public class UserFacade implements Serializable {

	private static final long serialVersionUID = 1L;
	private UserDAO userDAO = new UserDAO();

	public User isValidLogin(String login, String password) {
		password = Criptografia.criptografa(password);

		userDAO.beginTransaction();
		User user = userDAO.findUserByLogin(login);

		if (user != null) {
			if (user.getPassword() == null) {
				return null;
			}
			if (!user.getAtivo()) {
				return null;
			}
			if (user.getPassword().equals(password)) {
				user.setUltimoAcesso(Calendar.getInstance());
				userDAO.commit();
			} else {
				return null;
			}
		} else {
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

	public void createUsuario(User user) {
		userDAO.beginTransaction();
		userDAO.save(user);
		userDAO.commitAndCloseTransaction();
	}

	public User findUsuario(int userId) {
		userDAO.beginTransaction();
		User user = userDAO.find(userId);
		userDAO.closeTransaction();
		return user;
	}

	public List<User> listAll() {
		userDAO.beginTransaction();
		List<User> result = userDAO.findAllAsc();
		userDAO.closeTransaction();
		return result;
	}

	public void updateUsuario(User user) {
		userDAO.beginTransaction();
		User userFind = userDAO.find(user.getId());
		if (user.getName() != null)
			userFind.setName(user.getName());

		if (user.getEmail() != null)
			userFind.setEmail(user.getEmail());

		if (user.getLogin() != null)
			userFind.setLogin(user.getLogin());

		if (user.getPassword() != null)
			userFind.setPassword(user.getPassword());

		if (user.getRole() != null)
			userFind.setRole(user.getRole());


		if (user.getUltimoAcesso() != null)
			userFind.setUltimoAcesso(user.getUltimoAcesso());
		
		userFind.setAtivo(user.getAtivo());
		
		userDAO.update(userFind);
		userDAO.commitAndCloseTransaction();
	}
}