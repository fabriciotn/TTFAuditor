package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.AuditoriaDAO;
import com.model.Auditoria;
import com.model.Unidade;

public class AuditoriaFacade implements Serializable {

	private static final long		serialVersionUID	= 1L;

	private AuditoriaDAO			auditoriaDAO		= new AuditoriaDAO();
	private static SessionFactory	factory;

	private Auditoria				auditoria;

	public void createAuditoria(Auditoria auditoria) {
		auditoriaDAO.beginTransaction();
		auditoriaDAO.save(auditoria);
		auditoriaDAO.commitAndCloseTransaction();
	}

	public void updateAuditoria(Auditoria auditoria) {
		auditoriaDAO.beginTransaction();
		Auditoria persistedAuditoria = auditoriaDAO.find(auditoria.getId());
		if(persistedAuditoria != null){
			persistedAuditoria.setAuditores(auditoria.getAuditores());
			persistedAuditoria.setCodigo(auditoria.getCodigo());
			persistedAuditoria.setDataDaVerificacao(auditoria.getDataDaVerificacao());
			persistedAuditoria.setEstabelecimento(auditoria.getEstabelecimento());
			persistedAuditoria.setRespostas(auditoria.getRespostas());
			persistedAuditoria.setTipo(auditoria.getTipo());
			persistedAuditoria.setOffLine(auditoria.isOffLine());
			persistedAuditoria.setUser(auditoria.getUser());
		}else{
			persistedAuditoria = auditoria;
		}
		auditoriaDAO.update(persistedAuditoria);
		auditoriaDAO.commitAndCloseTransaction();
	}

	public Auditoria findAuditoria(int auditoriaId) {
		if (auditoria == null) {
			auditoriaDAO.beginTransaction();
			auditoria = auditoriaDAO.find(auditoriaId);
			auditoriaDAO.closeTransaction();
		}
		return auditoria;
	}

	public List<Auditoria> listAll() {
		auditoriaDAO.beginTransaction();
		List<Auditoria> result = auditoriaDAO.findAllAsc();
		auditoriaDAO.closeTransaction();
		return result;
	}
	
	public List<Auditoria> listAuditoriasOff() {
		auditoriaDAO.beginTransaction();
		List<Auditoria> result = auditoriaDAO.listaAuditoriasOff();
		auditoriaDAO.closeTransaction();
		return result;
	}

	public void deleteAuditoria(Auditoria auditoria) {
		auditoriaDAO.beginTransaction();
		Auditoria persistedAuditoria = auditoriaDAO.findReferenceOnly(auditoria.getId());
		auditoriaDAO.delete(persistedAuditoria);
		auditoriaDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		auditoriaDAO.beginTransaction();
		Query query = auditoriaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>) query.getResultList();
		auditoriaDAO.closeTransaction();
		return list;
	}
	
	public List<Auditoria> findAuditoriasOff(){
		auditoriaDAO.beginTransaction();
		List<Auditoria> result = auditoriaDAO.listaAuditoriasOff();
		auditoriaDAO.closeTransaction();
		return result;
	}

	public List<Auditoria> listAuditoriasPorUnidade(Unidade unidade) {
		auditoriaDAO.beginTransaction();
		List<Auditoria> result = auditoriaDAO.listaAuditoriaslistaPorUnidade(unidade);
		auditoriaDAO.closeTransaction();
		return result;
	}

	public List<Auditoria> listAuditoriasOffPorUnidade(Unidade unidade) {
		auditoriaDAO.beginTransaction();
		List<Auditoria> result = auditoriaDAO.listaAuditoriasOfflistaPorUnidade(unidade);
		auditoriaDAO.closeTransaction();
		return result;
	}
}