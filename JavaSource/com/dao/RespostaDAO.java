package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.model.Resposta;

/**
 * Classe de acesso ao BD - Resposta
 * @author TTF Informática
 *
 */
public class RespostaDAO extends GenericDAO<Resposta> {

	private static final long serialVersionUID = 1L;

	public RespostaDAO() {
		super(Resposta.class);
	}

	public void delete(Resposta resposta) {
		super.delete(resposta.getId(), Resposta.class);
	}
	
	/**
	 * Método para realizar a pesquisa de respostas de acordo com o ID da auditoria
	 * @param auditoria_id
	 * @return
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