package com.mb;
/*
import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.AuditorFacade;
import com.model.Auditor;
import com.model.User;
import com.util.RetiraMascaras;

@RequestScoped
@ManagedBean
public class AuditorMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Auditor auditor;
	private List<Auditor> auditores;
	private AuditorFacade auditorFacade;
	private User usuarioLogado;
	
	public AuditorMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public AuditorFacade getAuditorFacade() {
		if (auditorFacade == null) {
			auditorFacade = new AuditorFacade();
		}

		return auditorFacade;
	}

	public Auditor getAuditor() {
		if (auditor == null) {
			auditor = new Auditor();
		}

		return auditor;
	}

	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}

	public String createAuditor() {
		try {
			auditor.setUser(usuarioLogado);

			getAuditorFacade().createAuditor(auditor);
			closeDialog();
			displayInfoMessageToUser("Auditor " + auditor.getNome() + " gravado com sucesso!");
			loadAuditores();
			resetAuditor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarAuditor.xhtml";
	}

	public void updateAuditor() {
		try {
			auditor.setTelefone(RetiraMascaras.retirar(auditor.getTelefone()));
			auditor.setCelular(RetiraMascaras.retirar(auditor.getCelular()));
			
			if (auditor.getId() == 0) {
				createAuditor();
			} else {
				auditor.setUser(usuarioLogado);
				getAuditorFacade().updateAuditor(auditor);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadAuditores();
				resetAuditor();

				// retorna para a listagem de Auditors
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("listarAuditores.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteAuditor() {
		try {
			getAuditorFacade().deleteAuditor(auditor);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadAuditores();
			resetAuditor();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteAuditor(String id) {
		int idAuditor = Integer.parseInt(id);
		auditor = auditorFacade.findAuditor(idAuditor);
		deleteAuditor();
	}

	public List<Auditor> getAllAuditores() {
		if (auditores == null) {
			loadAuditores();
		}

		return auditores;
	}

	private void loadAuditores() {
		auditores = getAuditorFacade().listAll();
	}

	public void resetAuditor() {
		auditor = new Auditor();
	}

	public Auditor pesquisaAuditor() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int auditorId = id;
		auditor = auditorFacade.findAuditor(auditorId);

		return auditor;
	}
}
*/