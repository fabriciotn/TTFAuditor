package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.EstabelecimentoDAO;
import com.model.Estabelecimento;

public class EstabelecimentoFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private EstabelecimentoDAO estabelecimentoDAO = new EstabelecimentoDAO();
	private static SessionFactory factory; 

	public void createEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		estabelecimentoDAO.save(estabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	public void updateEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento persistedEstabelecimento = estabelecimentoDAO.find(estabelecimento.getId());
		persistedEstabelecimento.setCnpj(estabelecimento.getCnpj());
		persistedEstabelecimento.setCnes(estabelecimento.getCnes());
		persistedEstabelecimento.setNomeFantasia(estabelecimento.getNomeFantasia());
		persistedEstabelecimento.setRazaoSocial(estabelecimento.getRazaoSocial());
		persistedEstabelecimento.setUser(estabelecimento.getUser());

		estabelecimentoDAO.update(persistedEstabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	public Estabelecimento findEstabelecimento(int estabelecimentoId) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento estabelecimento = estabelecimentoDAO.find(estabelecimentoId);
		estabelecimentoDAO.closeTransaction();
		return estabelecimento;
	}

	public List<Estabelecimento> listAll() {
		estabelecimentoDAO.beginTransaction();
		List<Estabelecimento> result = estabelecimentoDAO.findAllAsc();
		estabelecimentoDAO.closeTransaction();
		return result;
	}

	public void deleteEstabelecimento(Estabelecimento estabelecimento) {
		estabelecimentoDAO.beginTransaction();
		Estabelecimento persistedEstabelecimento = estabelecimentoDAO.findReferenceOnly(estabelecimento
				.getId());
		estabelecimentoDAO.delete(persistedEstabelecimento);
		estabelecimentoDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		estabelecimentoDAO.beginTransaction();
		Query query = estabelecimentoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		estabelecimentoDAO.closeTransaction();
		return list;
	}

}