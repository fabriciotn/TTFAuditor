package com.dao;

import com.model.Parametros;

/**
 * Classe de acesso ao BD - Par�metros
 * @author TTF Inform�tica
 *
 */
public class ParametrosDAO extends GenericDAO<Parametros> {

	private static final long serialVersionUID = 1L;

	public ParametrosDAO() {
		super(Parametros.class);
	}

	public void delete(Parametros parametros) {
		super.delete(parametros.getId(), Parametros.class);
	}
}