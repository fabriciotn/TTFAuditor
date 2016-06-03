package com.testes;

import com.model.User;
import com.resource.UsuarioResource;

public class ArquivosTeste {

	public static void main(String[] args) {
		//UserFacade facade = new UserFacade();
		//User user = new User();
		//user = facade.isValidLogin("fabricio1", "fabricio");

		
		//RelatorioAuditoria relat = new RelatorioAuditoria();
		//relat.imprimeRelatorio();
		
		
		
		UsuarioResource.serializaUsuario(1);
		User user = UsuarioResource.deserializaUsuario();
		System.out.println(user.getName());
		System.out.println(user.getEmail());
	}
}