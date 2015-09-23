package com.mb;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import com.email.PendenciaCadastrada;
import com.facade.PendenciaFacade;
import com.model.Pendencia;
import com.model.User;

@ViewScoped
@ManagedBean
public class PendenciaMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pendencia pendencia;
	private List<Pendencia> pendencias;
	private PendenciaFacade pendenciaFacade;

	public PendenciaFacade getPendenciaFacade() {
		if (pendenciaFacade == null) {
			pendenciaFacade = new PendenciaFacade();
		}

		return pendenciaFacade;
	}

	public Pendencia getPendencia() {
		if (pendencia == null) {
			pendencia = new Pendencia();
		}

		return pendencia;
	}

	public void setPendencia(Pendencia pendencia) {
		this.pendencia = pendencia;
	}

	public String createPendencia() {
		try {
			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("user");
			
			if(user == null)
				throw new RuntimeException("Problemas com usuário");

			pendencia.setUsuario(user);

			// Seta a data de criação da pendencia
			pendencia.setDataDaPendencia(new GregorianCalendar());

			getPendenciaFacade().createPendencia(pendencia);
			closeDialog();
			displayInfoMessageToUser("Pendencia " + pendencia.getId() + " gravada com sucesso!");
			
			//envia e-mail
			PendenciaCadastrada.enviaEmail(pendencia);
			
			loadPendencias();
			resetPendencia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarPendencia.xhtml";
	}

	public void updatePendencia() {
		try {
			getPendenciaFacade().updatePendencia(pendencia);
			closeDialog();
			displayInfoMessageToUser("Atualizado com sucesso!");
			loadPendencias();
			resetPendencia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deletePendencia() {
		try {
			getPendenciaFacade().deletePendencia(pendencia);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadPendencias();
			resetPendencia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deletePendencia(String id) {
		int idPendencia = Integer.parseInt(id);
		pendencia = pendenciaFacade.findPendencia(idPendencia);
		deletePendencia();
	}

	public List<Pendencia> getAllPendencias() {
		if (pendencias == null) {
			loadPendencias();
		}

		return pendencias;
	}

	private void loadPendencias() {
		pendencias = getPendenciaFacade().listAll();
	}

	public void resetPendencia() {
		pendencia = new Pendencia();
	}
	
	public Pendencia pesquisaPendencia(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		
		int id = (Integer) session.getAttribute("id");
		int pendenciaId = id;
		pendencia = pendenciaFacade.findPendencia(pendenciaId);
		
		return pendencia;
	}
}
