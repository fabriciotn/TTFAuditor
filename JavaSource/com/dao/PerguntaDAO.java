package com.dao;

import com.model.Pergunta;

public class PerguntaDAO extends GenericDAO<Pergunta> {

	private static final long serialVersionUID = 1L;

	public PerguntaDAO() {
		super(Pergunta.class);
	}

	public void delete(Pergunta pergunta) {
		super.delete(pergunta.getId(), Pergunta.class);
	}
}