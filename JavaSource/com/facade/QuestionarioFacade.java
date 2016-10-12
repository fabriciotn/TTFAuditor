package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.QuestionarioDAO;
import com.model.Questionario;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author TTF Informática
 *
 */
public class QuestionarioFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private QuestionarioDAO questionarioDAO = new QuestionarioDAO();
	private static SessionFactory factory; 

	/**
	 * Cria um novo questionário
	 * @param questionario
	 */
	public void createQuestionario(Questionario questionario) {
		questionarioDAO.beginTransaction();
		questionarioDAO.save(questionario);
		questionarioDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um questionário
	 * @param questionario
	 */
	public void updateQuestionario(Questionario questionario) {
		questionarioDAO.beginTransaction();
		Questionario persistedQuestionario = questionarioDAO.find(questionario.getId());
		if(persistedQuestionario != null){
			persistedQuestionario.setTitulo(questionario.getTitulo());
		}else{
			persistedQuestionario = questionario;
		}

		questionarioDAO.update(persistedQuestionario);
		questionarioDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um questionário de acordo com o ID
	 * @param questionarioId
	 * @return questionario
	 */
	public Questionario findQuestionario(int questionarioId) {
		questionarioDAO.beginTransaction();
		Questionario questionario = questionarioDAO.find(questionarioId);
		questionarioDAO.closeTransaction();
		return questionario;
	}	

	/**
	 * Busca todos os questionários
	 * @return listaDeQuestionarios
	 */
	public List<Questionario> listAll() {
		questionarioDAO.beginTransaction();
		List<Questionario> result = questionarioDAO.findAllAsc();
		questionarioDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta um questionário
	 * @param questionario
	 */
	public void deleteQuestionario(Questionario questionario) {
		questionarioDAO.beginTransaction();
		Questionario persistedQuestionario = questionarioDAO.findReferenceOnly(questionario
				.getId());
		questionarioDAO.delete(persistedQuestionario);
		questionarioDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		questionarioDAO.beginTransaction();
		Query query = questionarioDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		questionarioDAO.closeTransaction();
		return list;
	}
}