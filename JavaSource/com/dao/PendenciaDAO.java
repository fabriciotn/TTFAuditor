package com.dao;

import com.model.Pendencia;

public class PendenciaDAO extends GenericDAO<Pendencia> {

	private static final long serialVersionUID = 1L;

	public PendenciaDAO() {
		super(Pendencia.class);
	}

	public void delete(Pendencia pendencia) {
		super.delete(pendencia.getId(), Pendencia.class);
	}
}
