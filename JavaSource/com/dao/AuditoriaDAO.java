package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.model.Auditoria;
import com.model.Unidade;

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
			      "AND a.dataDaVerificacao >= DATE_FORMAT(sysdate(),'%Y-%m-%d')", 
			      Auditoria.class);
		List<Auditoria> results = typedQuery.getResultList();
		return results;
	}

	public List<Auditoria> listaAuditoriaslistaPorUnidade(Unidade unidade) {
		TypedQuery<Auditoria> typedQuery = getEm().createQuery(
			      "SELECT a "+
			      "FROM Auditoria a "+
			      "WHERE a.estabelecimento in(SELECT e.id FROM Estabelecimento e WHERE e.unidade = :unidade)", 
			      Auditoria.class);
		typedQuery.setParameter("unidade", unidade);
		List<Auditoria> results = typedQuery.getResultList();
		return results;
	}
}