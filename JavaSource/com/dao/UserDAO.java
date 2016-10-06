package com.dao;
 
import java.util.HashMap;
import java.util.Map;
 
import com.model.User;

/**
 * Classe de acesso ao BD - Usuário
 * @author TTF Informática
 *
 */
public class UserDAO extends GenericDAO<User> {
 
	private static final long serialVersionUID = 1L;

	public UserDAO() {
        super(User.class);
    }
 
	/**
	 * Método utilizado para buscar um usuário pelo seu login
	 * @param login
	 * @return User
	 */
    public User findUserByLogin(String login){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("login", login);     
 
        return super.findOneResult(User.FIND_BY_LOGIN, parameters);
    }
}