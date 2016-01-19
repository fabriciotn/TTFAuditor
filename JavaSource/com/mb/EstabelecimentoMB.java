package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.EstabelecimentoFacade;
import com.model.Estabelecimento;
import com.model.User;
import com.util.RetiraMascaras;

@RequestScoped
@ManagedBean
public class EstabelecimentoMB extends AbstractMB implements Serializable {

	private static final long		serialVersionUID	= 1L;

	private Estabelecimento			estabelecimento;
	private List<Estabelecimento>	estabelecimentos;
	private EstabelecimentoFacade	estabelecimentoFacade;
	private User					usuarioLogado;
	private boolean					mostrar;

	public void teste() {
		int estabelecimento_id = 0;
		String idEmString = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estabelecimento_id");
		
		if(idEmString != null)
			estabelecimento_id = Integer.parseInt(idEmString);
		
		if (estabelecimento_id > 0)
			estabelecimento = getEstabelecimentoFacade().findEstabelecimento(estabelecimento_id);
	}

	public boolean isMostrar() {
		return mostrar;
	}

	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

	public EstabelecimentoMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public EstabelecimentoFacade getEstabelecimentoFacade() {
		if (estabelecimentoFacade == null) {
			estabelecimentoFacade = new EstabelecimentoFacade();
		}

		return estabelecimentoFacade;
	}

	public Estabelecimento getEstabelecimento() {
		if (estabelecimento == null) {
			estabelecimento = new Estabelecimento();
		}

		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public String createEstabelecimento() {
		try {
			estabelecimento.setUser(usuarioLogado);
			if(estabelecimento.getCnpj() != null && estabelecimento.getCnpj().isEmpty()){
				estabelecimento.setCnpj(null);
			}

			getEstabelecimentoFacade().createEstabelecimento(estabelecimento);
			closeDialog();
			displayInfoMessageToUser("Estabelecimento " + estabelecimento.getNomeFantasia() + " gravada com sucesso!");
			loadEstabelecimentos();
			resetEstabelecimento();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarEstabelecimento.xhtml";
	}

	public void updateEstabelecimento() {
		try {
			estabelecimento.setCnpj(RetiraMascaras.retirar(estabelecimento.getCnpj()));
			estabelecimento.setCep(RetiraMascaras.retirar(estabelecimento.getCep()));
			estabelecimento.setTelefone1(RetiraMascaras.retirar(estabelecimento.getTelefone1()));
			estabelecimento.setTelefone2(RetiraMascaras.retirar(estabelecimento.getTelefone2()));
			estabelecimento.setTelefone3(RetiraMascaras.retirar(estabelecimento.getTelefone3()));
			estabelecimento.setTelefone4(RetiraMascaras.retirar(estabelecimento.getTelefone4()));
			if(estabelecimento.getCnpj() != null && estabelecimento.getCnpj().isEmpty()){
				estabelecimento.setCnpj(null);
			}

			if (estabelecimento.getId() == 0) {
				createEstabelecimento();
			} else {
				estabelecimento.setUser(usuarioLogado);
				getEstabelecimentoFacade().updateEstabelecimento(estabelecimento);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadEstabelecimentos();
				resetEstabelecimento();

				// retorna para a listagem de Estabelecimentos
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("listarEstabelecimentos.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteEstabelecimento() {
		try {
			getEstabelecimentoFacade().deleteEstabelecimento(estabelecimento);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadEstabelecimentos();
			resetEstabelecimento();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteEstabelecimento(String id) {
		int idEstabelecimento = Integer.parseInt(id);
		estabelecimento = estabelecimentoFacade.findEstabelecimento(idEstabelecimento);
		deleteEstabelecimento();
	}

	public List<Estabelecimento> getAllEstabelecimentos() {
		if (estabelecimentos == null) {
			loadEstabelecimentos();
		}

		return estabelecimentos;
	}

	private void loadEstabelecimentos() {
		estabelecimentos = getEstabelecimentoFacade().listAll();
	}

	public void resetEstabelecimento() {
		estabelecimento = new Estabelecimento();
	}

	public Estabelecimento pesquisaEstabelecimento() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int estabelecimentoId = id;
		estabelecimento = estabelecimentoFacade.findEstabelecimento(estabelecimentoId);

		return estabelecimento;
	}

	public void exibir() {
		if (estabelecimento.isCirurgiaGrandePorte()) {
			mostrar = true;
		} else {
			mostrar = false;
		}
	}
}
