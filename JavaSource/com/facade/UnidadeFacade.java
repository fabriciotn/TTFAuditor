package com.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.UnidadeDAO;
import com.model.Unidade;

public class UnidadeFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private UnidadeDAO unidadeDAO = new UnidadeDAO();
	private static SessionFactory factory; 

	public void createUnidade(Unidade unidade) {
		unidadeDAO.beginTransaction();
		unidadeDAO.save(unidade);
		unidadeDAO.commitAndCloseTransaction();
	}

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

	public Unidade findUnidade(int unidadeId) {
		unidadeDAO.beginTransaction();
		Unidade unidade = unidadeDAO.find(unidadeId);
		unidadeDAO.closeTransaction();
		return unidade;
	}

	public List<Unidade> listAll() {
		unidadeDAO.beginTransaction();
		List<Unidade> result = unidadeDAO.findAllAsc();
		unidadeDAO.closeTransaction();
		return result;
	}

	public void deleteUnidade(Unidade unidade) {
		unidadeDAO.beginTransaction();
		Unidade persistedUnidade = unidadeDAO.findReferenceOnly(unidade
				.getId());
		unidadeDAO.delete(persistedUnidade);
		unidadeDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		unidadeDAO.beginTransaction();
		Query query = unidadeDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		unidadeDAO.closeTransaction();
		return list;
	}

}