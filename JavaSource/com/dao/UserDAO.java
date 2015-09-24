package com.dao;
 
import java.util.HashMap;
import java.util.Map;
 
import com.model.User;
 
public class UserDAO extends GenericDAO<User> {
 
	private static final long serialVersionUID = 1L;

	public UserDAO() {
        super(User.class);
    }
 
    public User findUserByLogin(String login){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("login", login);     
 
        return super.findOneResult(User.FIND_BY_LOGIN, parameters);
    }
}