package com.dao;

import com.model.Unidade;

/**
 * Classe de acesso ao BD - Unidade
 * @author TTF Informática
 *
 */
public class UnidadeDAO extends GenericDAO<Unidade> {

	private static final long serialVersionUID = 1L;

	public UnidadeDAO() {
		super(Unidade.class);
	}

	public void delete(Unidade unidade) {
		super.delete(unidade.getId(), Unidade.class);
	}
}