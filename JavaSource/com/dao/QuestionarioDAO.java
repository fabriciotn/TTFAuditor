package com.dao;

import com.model.Questionario;

/**
 * Classe de acesso ao BD - Questionario
 * @author TTF Informática
 *
 */
public class QuestionarioDAO extends GenericDAO<Questionario> {

	private static final long serialVersionUID = 1L;

	public QuestionarioDAO() {
		super(Questionario.class);
	}

	public void delete(Questionario questionario) {
		super.delete(questionario.getId(), Questionario.class);
	}
	
}