package com.dao;

import com.model.Setor;

public class SetorDAO extends GenericDAO<Setor> {

	private static final long serialVersionUID = 1L;

	public SetorDAO() {
		super(Setor.class);
	}

	public void delete(Setor setor) {
        	super.delete(setor.getId(), Setor.class);
	}
}
