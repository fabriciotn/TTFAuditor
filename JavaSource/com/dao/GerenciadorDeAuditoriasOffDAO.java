package com.dao;

import com.model.GerenciadorDeAuditoriasOff;

/**
 * CLASSE NÃO UTILIZADA NO MOMENTO
 * Será utilizada quando o sistema começar a utilizar WebServices 
 * para realizar as conexões entre os módulos on-line e off-line
 * @author TTF Informática
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