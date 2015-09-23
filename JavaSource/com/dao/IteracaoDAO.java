package com.dao;

import com.model.Iteracao;

public class IteracaoDAO extends GenericDAO<Iteracao>{

	private static final long serialVersionUID = 1L;

	public IteracaoDAO() {
		super(Iteracao.class);
	}

	public void delete(Iteracao iteracao) {
		super.delete(iteracao.getId(), Iteracao.class);
	}
}
