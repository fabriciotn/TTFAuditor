package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.IteracaoDAO;
import com.model.Iteracao;

public class IteracaoFacade implements Serializable{

	private static final long serialVersionUID = 1L;

	private IteracaoDAO iteracaoDAO = new IteracaoDAO();
	private static SessionFactory factory; 

	public void createIteracao(Iteracao iteracao) {
		iteracaoDAO.beginTransaction();
		iteracaoDAO.save(iteracao);
		iteracaoDAO.commitAndCloseTransaction();
	}

	public void updateIteracao(Iteracao iteracao) {
		iteracaoDAO.beginTransaction();
		Iteracao persistedIteracao = iteracaoDAO.find(iteracao.getId());
		persistedIteracao.setDataDaPendencia(iteracao.getDataDaPendencia());
		persistedIteracao.setDescricao(iteracao.getDescricao());
		persistedIteracao.setPendencia(iteracao.getPendencia());
		persistedIteracao.setDataEstimada(iteracao.getDataEstimada());
		persistedIteracao.setDataRealizada(iteracao.getDataRealizada());
		persistedIteracao.setPrioridade(iteracao.getPrioridade());

		iteracaoDAO.update(persistedIteracao);
		iteracaoDAO.commitAndCloseTransaction();
	}

	public Iteracao findIteracao(int iteracaoId) {
		iteracaoDAO.beginTransaction();
		Iteracao iteracao = iteracaoDAO.find(iteracaoId);
		iteracaoDAO.closeTransaction();
		return iteracao;
	}

	public List<Iteracao> listAll() {
		iteracaoDAO.beginTransaction();
		List<Iteracao> result = iteracaoDAO.findAllDesc();
		iteracaoDAO.closeTransaction();
		return result;
	}

	public void deleteIteracao(Iteracao iteracao) {
		iteracaoDAO.beginTransaction();
		Iteracao persistedIteracao = iteracaoDAO.findReferenceOnly(iteracao
				.getId());
		iteracaoDAO.delete(persistedIteracao);
		iteracaoDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		iteracaoDAO.beginTransaction();
		Query query = iteracaoDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		return list;
	}
}
