package com.testes;

import com.facade.SetorFacade;
import com.model.Setor;
import com.model.User;


public class ArquivosTeste {

	public static void main(String[] args) {
		SetorFacade facade = new SetorFacade();
		Setor setor = new Setor();
		setor = facade.findSetor(72);

		for (User usuario : setor.getUsuarios()) {
			System.out.println(usuario.getEmail());
		}
	}
}