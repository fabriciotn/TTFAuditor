package com.facade;

import java.io.Serializable;

import com.dao.ParametrosDAO;
import com.model.Parametros;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author TTF Informática
 *
 */
public class ParametrosFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private ParametrosDAO parametrosDAO = new ParametrosDAO();
	//private static SessionFactory factory; 

	/**
	 * Cria um novo parâmetro
	 * @param parametros
	 */
	public void createParametros(Parametros parametros) {
		parametrosDAO.beginTransaction();
		parametrosDAO.save(parametros);
		parametrosDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza um parâmetro
	 * @param parametros
	 */
	public void updateParametros(Parametros parametros) {
		parametrosDAO.beginTransaction();
		Parametros persistedParametros = parametrosDAO.find(parametros.getId());
		if(persistedParametros != null){
			persistedParametros.setQuantidadeDeDiasParaAlterarAResposta(parametros.getQuantidadeDeDiasParaAlterarAResposta());
		}else{
			persistedParametros = parametros;
		}
		parametrosDAO.update(persistedParametros);
		parametrosDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca um parâmetro de acordo com o ID
	 * @param parametrosId
	 * @return parametro
	 */
	public Parametros findParametros(int parametrosId) {
		parametrosDAO.beginTransaction();
		Parametros parametros = parametrosDAO.find(parametrosId);
		parametrosDAO.closeTransaction();
		return parametros;
	}

	/**
	 * Deleta um parâmetro
	 * @param parametros
	 */
	public void deleteParametros(Parametros parametros) {
		parametrosDAO.beginTransaction();
		Parametros persistedParametros = parametrosDAO.findReferenceOnly(1);
		parametrosDAO.delete(persistedParametros);
		parametrosDAO.commitAndCloseTransaction();
	}
}