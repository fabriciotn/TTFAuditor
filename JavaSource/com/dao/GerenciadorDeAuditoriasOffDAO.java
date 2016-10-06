package com.dao;

import com.model.GerenciadorDeAuditoriasOff;

/**
 * CLASSE N�O UTILIZADA NO MOMENTO
 * Ser� utilizada quando o sistema come�ar a utilizar WebServices 
 * para realizar as conex�es entre os m�dulos on-line e off-line
 * @author TTF Inform�tica
 *
 */
public class GerenciadorDeAuditoriasOffDAO extends GenericDAO<GerenciadorDeAuditoriasOff> {

	private static final long serialVersionUID = 1L;

	public GerenciadorDeAuditoriasOffDAO() {
		super(GerenciadorDeAuditoriasOff.class);
	}

	public void delete(GerenciadorDeAuditoriasOff gerenciador) {
		super.delete(gerenciador.getId(), GerenciadorDeAuditoriasOff.class);
	}
}