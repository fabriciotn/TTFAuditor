package com.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
		if(persistedGerenciador != null){
			persistedGerenciador.setAuditoria(gerenciador.getAuditoria());
			persistedGerenciador.setDataDownload(gerenciador.getDataDownload());
			persistedGerenciador.setDataUpload(gerenciador.getDataDownload());
			persistedGerenciador.setHostnameDownload(gerenciador.getHostnameDownload());
			persistedGerenciador.setHostnameUpload(gerenciador.getHostnameUpload());
			persistedGerenciador.setUsuarioDownload(gerenciador.getUsuarioDownload());
			persistedGerenciador.setUsuarioUpload(gerenciador.getUsuarioUpload());
		}else{
			persistedGerenciador = gerenciador;
		}
		gerenciadorDeAuditoriasOffDAO.update(persistedGerenciador);
		gerenciadorDeAuditoriasOffDAO.commitAndCloseTransaction();
	}

	public GerenciadorDeAuditoriasOff findGerenciador(int gerenciadorId) {
		gerenciadorDeAuditoriasOffDAO.beginTransaction();
		GerenciadorDeAuditoriasOff gerenciador = gerenciadorDeAuditoriasOffDAO.find(gerenciadorId);
		gerenciadorDeAuditoriasOffDAO.closeTransaction();
		return gerenciador;
	}
	
	public List<GerenciadorDeAuditoriasOff> listarPorAuditoria(int auditoria_id){
		gerenciadorDeAuditoriasOffDAO.beginTransaction();
		List<GerenciadorDeAuditoriasOff> todosRegistrosOff = gerenciadorDeAuditoriasOffDAO.findAllAsc();
		List<GerenciadorDeAuditoriasOff> registrosDaAuditoria = new ArrayList<GerenciadorDeAuditoriasOff>();
		for (GerenciadorDeAuditoriasOff gerenciador : todosRegistrosOff) {
			if(gerenciador.getAuditoria().getId() == auditoria_id){
				registrosDaAuditoria.add(gerenciador);
			}
		}
		
		return registrosDaAuditoria;
	}

	public void deleteGerenciador(GerenciadorDeAuditoriasOff gerenciador) {
		gerenciadorDeAuditoriasOffDAO.beginTransaction();
		GerenciadorDeAuditoriasOff persistedGerenciador = gerenciadorDeAuditoriasOffDAO.findReferenceOnly(1);
		gerenciadorDeAuditoriasOffDAO.delete(persistedGerenciador);
		gerenciadorDeAuditoriasOffDAO.commitAndCloseTransaction();
	}
}