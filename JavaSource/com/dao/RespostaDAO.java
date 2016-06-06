package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.model.Resposta;

public class RespostaDAO extends GenericDAO<Resposta> {

	private static final long serialVersionUID = 1L;

	public RespostaDAO() {
		super(Resposta.class);
	}

	public void delete(Resposta resposta) {
		super.delete(resposta.getId(), Resposta.class);
	}
	
	/**
	 * PESQUISA RESPOSTAS PELO ID DA AUDITORIA
	 */
	public List<Resposta> listaRespostasPorIdDaAuditoria(int auditoria_id) {
		TypedQuery<Resposta> typedQuery = getEm().createQuery(
			      "SELECT r "+
			      "FROM Resposta r "+
			      "WHERE r.auditoria = " + auditoria_id,
			      Resposta.class);
		List<Resposta> results = typedQuery.getResultList();
		return results;
	}
}