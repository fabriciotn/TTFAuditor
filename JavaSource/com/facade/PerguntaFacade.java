package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.PerguntaDAO;
import com.model.Pergunta;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author TTF Informática
 *
 */
public class PerguntaFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private PerguntaDAO perguntaDAO = new PerguntaDAO();
	private static SessionFactory factory; 

	/**
	 * Cria uma nova pergunta
	 * @param pergunta
	 */
	public void createPergunta(Pergunta pergunta) {
		perguntaDAO.beginTransaction();
		perguntaDAO.save(pergunta);
		perguntaDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza uma pergunta
	 * @param pergunta
	 */
	public void updatePergunta(Pergunta pergunta) {
		perguntaDAO.beginTransaction();
		Pergunta persistedPergunta = perguntaDAO.find(pergunta.getId());
		if(persistedPergunta != null){
			persistedPergunta.setHint(pergunta.getHint());	
			persistedPergunta.setPergunta(pergunta.getPergunta());
			persistedPergunta.setQuestionario(pergunta.getQuestionario());
			persistedPergunta.setRecomendacaoPadrao(pergunta.getRecomendacaoPadrao());
			persistedPergunta.setTipoServico(pergunta.getTipoServico());
			persistedPergunta.setUser(pergunta.getUser());
		}else{
			persistedPergunta = pergunta;
		}

		perguntaDAO.update(persistedPergunta);
		perguntaDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de uma pergunta de acordo com o ID
	 * @param perguntaId
	 * @return pergunta
	 */
	public Pergunta findPergunta(int perguntaId) {
		perguntaDAO.beginTransaction();
		Pergunta pergunta = perguntaDAO.find(perguntaId);
		perguntaDAO.closeTransaction();
		return pergunta;
	}

	/**
	 * Busca todas as perguntas
	 * @return listaDePerguntas
	 */
	public List<Pergunta> listAll() {
		String campo = "questionario_id";
		perguntaDAO.beginTransaction();
		List<Pergunta> result = perguntaDAO.findAllAsc("questionario");
		perguntaDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta uma pergunta
	 * @param pergunta
	 */
	public void deletePergunta(Pergunta pergunta) {
		perguntaDAO.beginTransaction();
		Pergunta persistedPergunta = perguntaDAO.findReferenceOnly(pergunta
				.getId());
		perguntaDAO.delete(persistedPergunta);
		perguntaDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		perguntaDAO.beginTransaction();
		Query query = perguntaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		perguntaDAO.closeTransaction();
		return list;
	}

}