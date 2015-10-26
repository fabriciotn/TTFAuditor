package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import com.facade.UnidadeFacade;
import com.model.Unidade;
import com.model.User;
import com.util.RetiraMascaras;

@RequestScoped
@ManagedBean
public class UnidadeMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Unidade unidade;
	private List<Unidade> unidades;
	private UnidadeFacade unidadeFacade;
	private User usuarioLogado;
	
	public UnidadeMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public UnidadeFacade getUnidadeFacade() {
		if (unidadeFacade == null) {
			unidadeFacade = new UnidadeFacade();
		}

		return unidadeFacade;
	}

	public Unidade getUnidade() {
		if (unidade == null) {
			unidade = new Unidade();
		}

		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public String createUnidade() {
		try {
			unidade.setUser(usuarioLogado);

			getUnidadeFacade().createUnidade(unidade);
			closeDialog();
			displayInfoMessageToUser("Unidade " + unidade.getSigla() + " gravada com sucesso!");
			loadUnidades();
			resetUnidade();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarUnidade.xhtml";
	}

	public void updateUnidade() {
		try {
			unidade.setTelefone(RetiraMascaras.retirar(unidade.getTelefone()));
			
			if (unidade.getId() == 0) {
				createUnidade();
			} else {
				unidade.setUser(usuarioLogado);
				getUnidadeFacade().updateUnidade(unidade);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadUnidades();
				resetUnidade();

				// retorna para a listagem de Unidades
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("listarUnidades.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteUnidade() {
		try {
			getUnidadeFacade().deleteUnidade(unidade);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadUnidades();
			resetUnidade();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteUnidade(String id) {
		int idUnidade = Integer.parseInt(id);
		unidade = unidadeFacade.findUnidade(idUnidade);
		deleteUnidade();
	}

	public List<Unidade> getAllUnidades() {
		if (unidades == null) {
			loadUnidades();
		}

		return unidades;
	}

	private void loadUnidades() {
		unidades = getUnidadeFacade().listAll();
	}

	public void resetUnidade() {
		unidade = new Unidade();
	}

	public Unidade pesquisaUnidade() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int unidadeId = id;
		unidade = unidadeFacade.findUnidade(unidadeId);

		return unidade;
	}

	public void showMessage(Unidade unid) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Endereço da unidade " + unid.getSigla(),
				unid.getEndereco());

		RequestContext.getCurrentInstance().showMessageInDialog(message);
	}
}
