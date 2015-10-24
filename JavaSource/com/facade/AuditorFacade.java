package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.AuditorDAO;
import com.model.Auditor;

public class AuditorFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private AuditorDAO auditorDAO = new AuditorDAO();
	private static SessionFactory factory; 

	public void createAuditor(Auditor auditor) {
		auditorDAO.beginTransaction();
		auditorDAO.save(auditor);
		auditorDAO.commitAndCloseTransaction();
	}

	public void updateAuditor(Auditor auditor) {
		auditorDAO.beginTransaction();
		Auditor persistedAuditor = auditorDAO.find(auditor.getId());
		persistedAuditor.setNome(auditor.getNome());		
		persistedAuditor.setMasp(auditor.getMasp());	
		persistedAuditor.setTelefone(auditor.getTelefone());
		persistedAuditor.setCelular(auditor.getCelular());
		persistedAuditor.setEmail(auditor.getEmail());
		persistedAuditor.setCargo(auditor.getCargo());
		persistedAuditor.setUser(auditor.getUser());

		auditorDAO.update(persistedAuditor);
		auditorDAO.commitAndCloseTransaction();
	}

	public Auditor findAuditor(int auditorId) {
		auditorDAO.beginTransaction();
		Auditor auditor = auditorDAO.find(auditorId);
		auditorDAO.closeTransaction();
		return auditor;
	}

	public List<Auditor> listAll() {
		auditorDAO.beginTransaction();
		List<Auditor> result = auditorDAO.findAllAsc();
		auditorDAO.closeTransaction();
		return result;
	}

	public void deleteAuditor(Auditor auditor) {
		auditorDAO.beginTransaction();
		Auditor persistedAuditor = auditorDAO.findReferenceOnly(auditor
				.getId());
		auditorDAO.delete(persistedAuditor);
		auditorDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		auditorDAO.beginTransaction();
		Query query = auditorDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		auditorDAO.closeTransaction();
		return list;
	}

}