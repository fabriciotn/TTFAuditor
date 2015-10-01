package com.testes;

import com.facade.UserFacade;
import com.model.User;

public class ArquivosTeste {

	public static void main(String[] args) {
		//UserFacade facade = new UserFacade();
		//User user = new User();
		//user = facade.isValidLogin("fabricio1", "fabricio");

		
		User user = new User();
		user.setName("Fabricio");
		user.setLogin("fabricio1");
		user.setPassword("fabricio");
		user.setEmail("fabriciotn@yahoo.com.br");
		
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		System.out.println(user.getEmail());
		System.out.println(user.getId());
		
		
		

	}
}