package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.UnidadeDAO;
import com.model.Unidade;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author TTF Informática
 *
 */
public class UnidadeFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private UnidadeDAO unidadeDAO = new UnidadeDAO();
	private static SessionFactory factory; 

	/**
	 * Cria uma nova unidade
	 * @param unidade
	 */
	public void createUnidade(Unidade unidade) {
		unidadeDAO.beginTransaction();
		unidadeDAO.save(unidade);
		unidadeDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza uma unidade
	 * @param unidade
	 */
	public void updateUnidade(Unidade unidade) {
		unidadeDAO.beginTransaction();
		Unidade persistedUnidade = unidadeDAO.find(unidade.getId());
		if(persistedUnidade != null){
			persistedUnidade.setNome(unidade.getNome());
			persistedUnidade.setSigla(unidade.getSigla());
			persistedUnidade.setTelefone(unidade.getTelefone());
			persistedUnidade.setContato(unidade.getContato());
			persistedUnidade.setEmail(unidade.getEmail());
			persistedUnidade.setEndereco(unidade.getEndereco());
			persistedUnidade.setUser(unidade.getUser());
		}else{
			persistedUnidade = unidade;
		}
			
		unidadeDAO.update(persistedUnidade);
		unidadeDAO.commitAndCloseTransaction();
	}

	/**
	 * Busca uma unidade de acordo com o ID
	 * @param unidadeId
	 * @return
	 */
	public Unidade findUnidade(int unidadeId) {
		unidadeDAO.beginTransaction();
		Unidade unidade = unidadeDAO.find(unidadeId);
		unidadeDAO.closeTransaction();
		return unidade;
	}

	/**
	 * Lista todas as unidades
	 * @return listaDeUnidades
	 */
	public List<Unidade> listAll() {
		unidadeDAO.beginTransaction();
		List<Unidade> result = unidadeDAO.findAllAsc();
		unidadeDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta uma unidade
	 * @param unidade
	 */
	public void deleteUnidade(Unidade unidade) {
		unidadeDAO.beginTransaction();
		Unidade persistedUnidade = unidadeDAO.findReferenceOnly(unidade
				.getId());
		unidadeDAO.delete(persistedUnidade);
		unidadeDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query passada via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		unidadeDAO.beginTransaction();
		Query query = unidadeDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		unidadeDAO.closeTransaction();
		return list;
	}

}