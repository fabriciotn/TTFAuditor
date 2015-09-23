package com.facade;

import java.io.Serializable;
import java.util.List;

import com.dao.SetorDAO;
import com.model.Setor;

public class SetorFacade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private SetorDAO setorDAO = new SetorDAO();

	public void createSetor(Setor setor) {
		setorDAO.beginTransaction();
		setorDAO.save(setor);
		setorDAO.commitAndCloseTransaction();
	}

	public void updateSetor(Setor setor) {
		setorDAO.beginTransaction();
		Setor persistedSetor = setorDAO.find(setor.getId());
		persistedSetor.setNome(setor.getNome());
		setorDAO.commitAndCloseTransaction();
	}
	
	public void deleteSetor(Setor setor){
		setorDAO.beginTransaction();
		Setor persistedSetorWithIdOnly = setorDAO.findReferenceOnly(setor.getId());
		setorDAO.delete(persistedSetorWithIdOnly);
		setorDAO.commitAndCloseTransaction();
		
	}

	public Setor findSetor(int setorId) {
		setorDAO.beginTransaction();
		Setor setor = setorDAO.find(setorId);
		setorDAO.closeTransaction();
		return setor;
	}

	public List<Setor> listAll() {
		setorDAO.beginTransaction();
		List<Setor> result = setorDAO.findAllAsc();
		setorDAO.closeTransaction();

		return result;
	}
}