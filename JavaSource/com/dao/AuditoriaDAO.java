package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.model.Auditoria;

public class AuditoriaDAO extends GenericDAO<Auditoria> {

	private static final long serialVersionUID = 1L;

	public AuditoriaDAO() {
		super(Auditoria.class);
	}

	public void delete(Auditoria auditoria) {
		super.delete(auditoria.getId(), Auditoria.class);
	}
	

	/*
	 * PESQUISA AUDITORIAS OFFLINE QUE A DATA SEJA >= A DATA ATUAL
	 */
	public List<Auditoria> listaAuditoriasOff() {
		TypedQuery<Auditoria> typedQuery = getEm().createQuery(
			      "SELECT a "+
			      "FROM Auditoria a "+
			      "WHERE a.offLine=1 "+
			      "AND a.dataDaVerificacao >= sysdate()", 
			      Auditoria.class);
		List<Auditoria> results = typedQuery.getResultList();
		return results;
	}
}