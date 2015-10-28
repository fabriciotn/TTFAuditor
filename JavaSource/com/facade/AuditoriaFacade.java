package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.AuditoriaDAO;
import com.model.Auditoria;

public class AuditoriaFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
	private static SessionFactory factory; 

	public void createAuditoria(Auditoria auditoria) {
		auditoriaDAO.beginTransaction();
		auditoriaDAO.save(auditoria);
		auditoriaDAO.commitAndCloseTransaction();
	}

	public void updateAuditoria(Auditoria auditoria) {
		auditoriaDAO.beginTransaction();
		Auditoria persistedAuditoria = auditoriaDAO.find(auditoria.getId());
//		persistedAuditoria.setNome(auditoria.getNome());		
//		persistedAuditoria.setMasp(auditoria.getMasp());	
//		persistedAuditoria.setTelefone(auditoria.getTelefone());
//		persistedAuditoria.setCelular(auditoria.getCelular());
//		persistedAuditoria.setEmail(auditoria.getEmail());
//		persistedAuditoria.setCargo(auditoria.getCargo());
		persistedAuditoria.setUser(auditoria.getUser());

		auditoriaDAO.update(persistedAuditoria);
		auditoriaDAO.commitAndCloseTransaction();
	}

	public Auditoria findAuditoria(int auditoriaId) {
		auditoriaDAO.beginTransaction();
		Auditoria auditoria = auditoriaDAO.find(auditoriaId);
		auditoriaDAO.closeTransaction();
		return auditoria;
	}

	public List<Auditoria> listAll() {
		auditoriaDAO.beginTransaction();
		List<Auditoria> result = auditoriaDAO.findAllAsc();
		auditoriaDAO.closeTransaction();
		return result;
	}

	public void deleteAuditoria(Auditoria auditoria) {
		auditoriaDAO.beginTransaction();
		Auditoria persistedAuditoria = auditoriaDAO.findReferenceOnly(auditoria
				.getId());
		auditoriaDAO.delete(persistedAuditoria);
		auditoriaDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		auditoriaDAO.beginTransaction();
		Query query = auditoriaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		auditoriaDAO.closeTransaction();
		return list;
	}

}