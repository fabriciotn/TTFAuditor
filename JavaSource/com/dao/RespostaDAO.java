package com.dao;

import com.model.Resposta;

public class RespostaDAO extends GenericDAO<Resposta> {

	private static final long serialVersionUID = 1L;

	public RespostaDAO() {
		super(Resposta.class);
	}

	public void delete(Resposta resposta) {
		super.delete(resposta.getId(), Resposta.class);
	}
}