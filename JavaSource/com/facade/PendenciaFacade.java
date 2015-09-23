package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.PendenciaDAO;
import com.email.PendenciaCadastrada;
import com.model.Pendencia;

public class PendenciaFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private PendenciaDAO pendenciaDAO = new PendenciaDAO();
	private static SessionFactory factory; 

	public void createPendencia(Pendencia pendencia) {
		pendenciaDAO.beginTransaction();
		pendenciaDAO.save(pendencia);
		pendenciaDAO.commitAndCloseTransaction();
	}

	public void updatePendencia(Pendencia pendencia) {
		pendenciaDAO.beginTransaction();
		Pendencia persistedPendencia = pendenciaDAO.find(pendencia.getId());
		persistedPendencia.setTitulo(pendencia.getTitulo());
		persistedPendencia.setDataDaPendencia(pendencia.getDataDaPendencia());
		persistedPendencia.setDescricao(pendencia.getDescricao());
		persistedPendencia.setItemDoEdital(pendencia.getItemDoEdital());
		persistedPendencia.setObs(pendencia.getObs());
		persistedPendencia.setPrioridade(pendencia.getPrioridade());
		persistedPendencia.setSetor(pendencia.getSetor());
		persistedPendencia.setStatus(pendencia.getStatus());
		persistedPendencia.setCategoria(pendencia.getCategoria());

		pendenciaDAO.update(persistedPendencia);
		pendenciaDAO.commitAndCloseTransaction();
	}

	public Pendencia findPendencia(int pendenciaId) {
		pendenciaDAO.beginTransaction();
		Pendencia pendencia = pendenciaDAO.find(pendenciaId);
		pendenciaDAO.closeTransaction();
		return pendencia;
	}

	public List<Pendencia> listAll() {
		pendenciaDAO.beginTransaction();
		List<Pendencia> result = pendenciaDAO.findAllAsc();
		pendenciaDAO.closeTransaction();
		return result;
	}

	public void deletePendencia(Pendencia pendencia) {
		pendenciaDAO.beginTransaction();
		Pendencia persistedPendencia = pendenciaDAO.findReferenceOnly(pendencia
				.getId());
		pendenciaDAO.delete(persistedPendencia);
		pendenciaDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		pendenciaDAO.beginTransaction();
		Query query = pendenciaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		pendenciaDAO.closeTransaction();
		return list;
	}

}
