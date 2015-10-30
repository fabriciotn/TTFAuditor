package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.RespostaDAO;
import com.model.Resposta;

public class RespostaFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private RespostaDAO respostaDAO = new RespostaDAO();
	private static SessionFactory factory; 

	public void createResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		respostaDAO.save(resposta);
		respostaDAO.commitAndCloseTransaction();
	}

	public void updateResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		Resposta persistedResposta = respostaDAO.find(resposta.getId());
		//persistedResposta.setResposta(resposta.getResposta()); NAO DEVE ATUALIZAR AS RESPOSTAS		
		persistedResposta.setUser(resposta.getUser());

		respostaDAO.update(persistedResposta);
		respostaDAO.commitAndCloseTransaction();
	}

	public Resposta findResposta(int respostaId) {
		respostaDAO.beginTransaction();
		Resposta resposta = respostaDAO.find(respostaId);
		respostaDAO.closeTransaction();
		return resposta;
	}

	public List<Resposta> listAll() {
		respostaDAO.beginTransaction();
		List<Resposta> result = respostaDAO.findAllAsc();
		respostaDAO.closeTransaction();
		return result;
	}

	public void deleteResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		Resposta persistedResposta = respostaDAO.findReferenceOnly(resposta
				.getId());
		respostaDAO.delete(persistedResposta);
		respostaDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		respostaDAO.beginTransaction();
		Query query = respostaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		respostaDAO.closeTransaction();
		return list;
	}

}