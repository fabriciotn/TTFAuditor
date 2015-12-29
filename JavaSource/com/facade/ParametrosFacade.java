package com.facade;

import java.io.Serializable;

import com.dao.ParametrosDAO;
import com.model.Parametros;

public class ParametrosFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private ParametrosDAO parametrosDAO = new ParametrosDAO();
	//private static SessionFactory factory; 

	public void createParametros(Parametros parametros) {
		parametrosDAO.beginTransaction();
		parametrosDAO.save(parametros);
		parametrosDAO.commitAndCloseTransaction();
	}

	public void updateParametros(Parametros parametros) {
		parametrosDAO.beginTransaction();
		Parametros persistedParametros = parametrosDAO.find(parametros.getId());
		persistedParametros.setQuantidadeDeDiasParaAlterarAResposta(parametros.getQuantidadeDeDiasParaAlterarAResposta());
		parametrosDAO.update(persistedParametros);
		parametrosDAO.commitAndCloseTransaction();
	}

	public Parametros findParametros(int parametrosId) {
		parametrosDAO.beginTransaction();
		Parametros parametros = parametrosDAO.find(parametrosId);
		parametrosDAO.closeTransaction();
		return parametros;
	}

	public void deleteParametros(Parametros parametros) {
		parametrosDAO.beginTransaction();
		Parametros persistedParametros = parametrosDAO.findReferenceOnly(1);
		parametrosDAO.delete(persistedParametros);
		parametrosDAO.commitAndCloseTransaction();
	}
}