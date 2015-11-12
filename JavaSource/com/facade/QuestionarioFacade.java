package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.AuditoriaDAO;
import com.dao.QuestionarioDAO;
import com.model.Auditoria;
import com.model.Questionario;

public class QuestionarioFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private QuestionarioDAO questionarioDAO = new QuestionarioDAO();
	private static SessionFactory factory; 

	public void createQuestionario(Questionario questionario) {
		questionarioDAO.beginTransaction();
		questionarioDAO.save(questionario);
		questionarioDAO.commitAndCloseTransaction();
	}

	public void updateQuestionario(Questionario questionario) {
		questionarioDAO.beginTransaction();
		Questionario persistedQuestionario = questionarioDAO.find(questionario.getId());
		persistedQuestionario.setTitulo(questionario.getTitulo());		
		persistedQuestionario.setAuditor(questionario.getAuditor());	
		persistedQuestionario.setDataDaAvaliacao(questionario.getDataDaAvaliacao());			
		persistedQuestionario.setUser(questionario.getUser());

		questionarioDAO.update(persistedQuestionario);
		questionarioDAO.commitAndCloseTransaction();
	}

	public Questionario findQuestionario(int questionarioId) {
		questionarioDAO.beginTransaction();
		Questionario questionario = questionarioDAO.find(questionarioId);
		questionarioDAO.closeTransaction();
		return questionario;
	}
	
	public List<Questionario> findQuestionarios(int auditoriaId) {
		questionarioDAO.beginTransaction();
		AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
		auditoriaDAO.beginTransaction();
		Auditoria auditoria = auditoriaDAO.find(auditoriaId);
		auditoriaDAO.closeTransaction();
		List<Questionario> questionarios = auditoria.getQuestionarios();
		questionarioDAO.closeTransaction();
		return questionarios;
	}

	public List<Questionario> listAll() {
		questionarioDAO.beginTransaction();
		List<Questionario> result = questionarioDAO.findAllAsc();
		questionarioDAO.closeTransaction();
		return result;
	}

	public void deleteQuestionario(Questionario questionario) {
		questionarioDAO.beginTransaction();
		Questionario persistedQuestionario = questionarioDAO.findReferenceOnly(questionario
				.getId());
		questionarioDAO.delete(persistedQuestionario);
		questionarioDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		questionarioDAO.beginTransaction();
		Query query = questionarioDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		questionarioDAO.closeTransaction();
		return list;
	}

}