package com.dao;
 
import java.util.HashMap;
import java.util.Map;
 
import com.model.User;
 
public class UserDAO extends GenericDAO<User> {
 
	private static final long serialVersionUID = 1L;

	public UserDAO() {
        super(User.class);
    }
 
    public User findUserByMasp(String masp){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("masp", masp);     
 
        return super.findOneResult(User.FIND_BY_MASP, parameters);
    }
}