package com.dao;

import com.model.Estabelecimento;

public class EstabelecimentoDAO extends GenericDAO<Estabelecimento> {

	private static final long serialVersionUID = 1L;

	public EstabelecimentoDAO() {
		super(Estabelecimento.class);
	}

	public void delete(Estabelecimento estabelecimento) {
		super.delete(estabelecimento.getId(), Estabelecimento.class);
	}
}