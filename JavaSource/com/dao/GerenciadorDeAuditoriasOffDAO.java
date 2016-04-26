package com.dao;

import com.model.GerenciadorDeAuditoriasOff;

public class GerenciadorDeAuditoriasOffDAO extends GenericDAO<GerenciadorDeAuditoriasOff> {

	private static final long serialVersionUID = 1L;

	public GerenciadorDeAuditoriasOffDAO() {
		super(GerenciadorDeAuditoriasOff.class);
	}

	public void delete(GerenciadorDeAuditoriasOff gerenciador) {
		super.delete(gerenciador.getId(), GerenciadorDeAuditoriasOff.class);
	}
}