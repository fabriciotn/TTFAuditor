package com.facade;

import java.io.Serializable;
import java.util.List;

import com.dao.ProjetoDAO;
import com.model.Projeto;

public class ProjetoFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ProjetoDAO projetoDAO = new ProjetoDAO();

	public void createProjeto(Projeto projeto) {
		projetoDAO.beginTransaction();
		projetoDAO.save(projeto);
		projetoDAO.commitAndCloseTransaction();
	}

	public void updateProjeto(Projeto projeto) {
		projetoDAO.beginTransaction();
		Projeto persistedProjeto = projetoDAO.find(projeto.getId());
		persistedProjeto.setNome(projeto.getNome());
		projetoDAO.update(persistedProjeto);
		projetoDAO.commitAndCloseTransaction();
	}

	public Projeto findProjeto(int projetoId) {
		projetoDAO.beginTransaction();
		Projeto projeto = projetoDAO.find(projetoId);
		projetoDAO.closeTransaction();
		return projeto;
	}

	public List<Projeto> listAll() {
		projetoDAO.beginTransaction();
		List<Projeto> result = projetoDAO.findAllAsc();
		projetoDAO.closeTransaction();
		return result;
	}

	public void deleteProjeto(Projeto projeto) {
		projetoDAO.beginTransaction();
		Projeto persistedProjeto = projetoDAO.findReferenceOnly(projeto.getId());
		projetoDAO.delete(persistedProjeto);
		projetoDAO.commitAndCloseTransaction();
	}
}