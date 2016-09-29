package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.model.Estabelecimento;
import com.model.Unidade;

/**
 * Classe de acesso ao BD - Estabelecimento
 * @author TTF Informática
 *
 */
public class EstabelecimentoDAO extends GenericDAO<Estabelecimento> {

	private static final long serialVersionUID = 1L;

	public EstabelecimentoDAO() {
		super(Estabelecimento.class);
	}

	/**
	 * Método para deletar estabelecimento
	 * @param estabelecimento
	 */
	public void delete(Estabelecimento estabelecimento) {
		super.delete(estabelecimento.getId(), Estabelecimento.class);
	}

	/**
	 * Método para listar estabelecimentos por unidade
	 * @param unidade
	 * @return List<Estabelecimento> listaDeEstabelecimentoPorUnidade
	 */
	public List<Estabelecimento> listaEstabelecimentoPorUnidade(Unidade unidade) {
		TypedQuery<Estabelecimento> typedQuery = getEm().createQuery(
			      "SELECT e "
			      + "FROM Estabelecimento e "
			      + "WHERE e.unidade = :unidade)", 
			      Estabelecimento.class);
		typedQuery.setParameter("unidade", unidade);
		List<Estabelecimento> results = typedQuery.getResultList();
		return results;
	}
}