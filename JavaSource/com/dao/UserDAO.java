package com.dao;
 
import java.util.HashMap;
import java.util.Map;
 
import com.model.User;

/**
 * Classe de acesso ao BD - Usu�rio
 * @author TTF Inform�tica
 *
 */
public class UserDAO extends GenericDAO<User> {
 
	private static final long serialVersionUID = 1L;

	public UserDAO() {
        super(User.class);
    }
 
	/**
	 * M�todo utilizado para buscar um usu�rio pelo seu login
	 * @param login
	 * @return User
	 */
    public User findUserByLogin(String login){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("login", login);     
 
        return super.findOneResult(User.FIND_BY_LOGIN, parameters);
    }
}