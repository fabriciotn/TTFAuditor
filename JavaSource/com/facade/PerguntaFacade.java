package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.PerguntaDAO;
import com.model.Pergunta;

public class PerguntaFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private PerguntaDAO perguntaDAO = new PerguntaDAO();
	private static SessionFactory factory; 

	public void createPergunta(Pergunta pergunta) {
		perguntaDAO.beginTransaction();
		perguntaDAO.save(pergunta);
		perguntaDAO.commitAndCloseTransaction();
	}

	public void updatePergunta(Pergunta pergunta) {
		perguntaDAO.beginTransaction();
		Pergunta persistedPergunta = perguntaDAO.find(pergunta.getId());
		persistedPergunta.setHint(pergunta.getHint());	
		persistedPergunta.setPergunta(pergunta.getPergunta());
		persistedPergunta.setQuestionario(pergunta.getQuestionario());
		persistedPergunta.setRecomendacaoPadrao(pergunta.getRecomendacaoPadrao());
		persistedPergunta.setTipoServico(pergunta.getTipoServico());
		persistedPergunta.setUser(pergunta.getUser());

		perguntaDAO.update(persistedPergunta);
		perguntaDAO.commitAndCloseTransaction();
	}

	public Pergunta findPergunta(int perguntaId) {
		perguntaDAO.beginTransaction();
		Pergunta pergunta = perguntaDAO.find(perguntaId);
		perguntaDAO.closeTransaction();
		return pergunta;
	}

	public List<Pergunta> listAll() {
		perguntaDAO.beginTransaction();
		List<Pergunta> result = perguntaDAO.findAllAsc();
		perguntaDAO.closeTransaction();
		return result;
	}

	public void deletePergunta(Pergunta pergunta) {
		perguntaDAO.beginTransaction();
		Pergunta persistedPergunta = perguntaDAO.findReferenceOnly(pergunta
				.getId());
		perguntaDAO.delete(persistedPergunta);
		perguntaDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		perguntaDAO.beginTransaction();
		Query query = perguntaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		perguntaDAO.closeTransaction();
		return list;
	}

}