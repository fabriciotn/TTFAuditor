package com.dao;

import com.model.Parametros;

/**
 * Classe de acesso ao BD - Parâmetros
 * @author TTF Informática
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