package com.dao;

import com.model.Projeto;

public class ProjetoDAO extends GenericDAO<Projeto> {

	private static final long serialVersionUID = 1L;

	public ProjetoDAO() {
		super(Projeto.class);
	}

	public void delete(Projeto projeto) {
		super.delete(projeto.getId(), Projeto.class);
	}

}