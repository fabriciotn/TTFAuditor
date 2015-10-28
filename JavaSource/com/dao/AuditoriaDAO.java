package com.dao;

import com.model.Auditoria;

public class AuditoriaDAO extends GenericDAO<Auditoria> {

	private static final long serialVersionUID = 1L;

	public AuditoriaDAO() {
		super(Auditoria.class);
	}

	public void delete(Auditoria auditoria) {
		super.delete(auditoria.getId(), Auditoria.class);
	}
}