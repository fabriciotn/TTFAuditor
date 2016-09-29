package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.model.Auditoria;
import com.model.Unidade;

/**
 * Classe de acesso ao BD - Auditoria
 * @author TTF Inform�tica
 *
 */
public class AuditoriaDAO extends GenericDAO<Auditoria> {

	private static final long serialVersionUID = 1L;

	public AuditoriaDAO() {
		super(Auditoria.class);
	}

	/**
	 * M�todo para deletar auditoria
	 * @param auditoria
	 */
	public void delete(Auditoria auditoria) {
		super.delete(auditoria.getId(), Auditoria.class);
	}
	

	/**
	 * M�todo para listar todas auditorias Off-line
	 * @return List<Auditoria> listaDeAuditoriasOff
	 */
	public List<Auditoria> listaAuditoriasOff() {
		TypedQuery<Auditoria> typedQuery = getEm().createQuery(
			      "SELECT a "+
			      "FROM Auditoria a "+
			      "WHERE a.offLine=1 ORDER BY a.dataDaVerificacao desc",
			      //+"AND a.dataDaVerificacao >= DATE_FORMAT(sysdate(),'%Y-%m-%d')", //PESQUISA AUDITORIAS OFFLINE QUE A DATA SEJA >= A DATA ATUAL
			      Auditoria.class);
		List<Auditoria> results = typedQuery.getResultList();
		return results;
	}

	/**
	 * M�todo para listar auditorias por unidade
	 * @param unidade
	 * @return List<Auditoria> listaDeAuditoriasPorUnidade
	 */
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

	/**
	 * M�todo para listar auditorias Off-line por unidade
	 * @param unidade
	 * @return List<Auditoria> listaDeAuditoriasOffPorUnidade
	 */
	public List<Auditoria> listaAuditoriasOfflistaPorUnidade(Unidade unidade) {
		TypedQuery<Auditoria> typedQuery = getEm().createQuery(
			      "SELECT a "+
			      "FROM Auditoria a "+
			      "WHERE a.estabelecimento in(SELECT e.id FROM Estabelecimento e WHERE e.unidade = :unidade) " +
			      "AND a.offLine = 1", 
			      Auditoria.class);
		typedQuery.setParameter("unidade", unidade);
		List<Auditoria> results = typedQuery.getResultList();
		return results;
	}
}