package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.model.Estabelecimento;
import com.model.Unidade;

public class EstabelecimentoDAO extends GenericDAO<Estabelecimento> {

	private static final long serialVersionUID = 1L;

	public EstabelecimentoDAO() {
		super(Estabelecimento.class);
	}

	public void delete(Estabelecimento estabelecimento) {
		super.delete(estabelecimento.getId(), Estabelecimento.class);
	}

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