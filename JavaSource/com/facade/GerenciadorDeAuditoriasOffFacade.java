package com.facade;

import java.io.Serializable;

import com.dao.GerenciadorDeAuditoriasOffDAO;
import com.model.GerenciadorDeAuditoriasOff;

public class GerenciadorDeAuditoriasOffFacade implements Serializable {

	private static final long serialVersionUID = 1L;

	private GerenciadorDeAuditoriasOffDAO gerenciadorDeAuditoriasOffDAO = new GerenciadorDeAuditoriasOffDAO();
	//private static SessionFactory factory; 

	public void createGerenciador(GerenciadorDeAuditoriasOff gerenciador) {
		gerenciadorDeAuditoriasOffDAO.beginTransaction();
		gerenciadorDeAuditoriasOffDAO.save(gerenciador);
		gerenciadorDeAuditoriasOffDAO.commitAndCloseTransaction();
	}

	public void updateGerenciador(GerenciadorDeAuditoriasOff gerenciador) {
		gerenciadorDeAuditoriasOffDAO.beginTransaction();
		GerenciadorDeAuditoriasOff persistedGerenciador = gerenciadorDeAuditoriasOffDAO.find(gerenciador.getId());
		persistedGerenciador.setAuditoria(gerenciador.getAuditoria());
		persistedGerenciador.setDataDownload(gerenciador.getDataDownload());
		persistedGerenciador.setDataUpload(gerenciador.getDataDownload());
		persistedGerenciador.setHostnameDownload(gerenciador.getHostnameDownload());
		persistedGerenciador.setHostnameUpload(gerenciador.getHostnameUpload());
		persistedGerenciador.setUsuarioDownload(gerenciador.getUsuarioDownload());
		persistedGerenciador.setUsuarioUpload(gerenciador.getUsuarioUpload());
		gerenciadorDeAuditoriasOffDAO.update(persistedGerenciador);
		gerenciadorDeAuditoriasOffDAO.commitAndCloseTransaction();
	}

	public GerenciadorDeAuditoriasOff findGerenciador(int gerenciadorId) {
		gerenciadorDeAuditoriasOffDAO.beginTransaction();
		GerenciadorDeAuditoriasOff gerenciador = gerenciadorDeAuditoriasOffDAO.find(gerenciadorId);
		gerenciadorDeAuditoriasOffDAO.closeTransaction();
		return gerenciador;
	}

	public void deleteGerenciador(GerenciadorDeAuditoriasOff gerenciador) {
		gerenciadorDeAuditoriasOffDAO.beginTransaction();
		GerenciadorDeAuditoriasOff persistedGerenciador = gerenciadorDeAuditoriasOffDAO.findReferenceOnly(1);
		gerenciadorDeAuditoriasOffDAO.delete(persistedGerenciador);
		gerenciadorDeAuditoriasOffDAO.commitAndCloseTransaction();
	}
}