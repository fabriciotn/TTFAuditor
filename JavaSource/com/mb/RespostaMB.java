package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.RespostaFacade;
import com.model.Pergunta;
import com.model.Resposta;
import com.model.User;

@SessionScoped
@ManagedBean
public class RespostaMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Resposta resposta;
	private List<Resposta> respostas;
	private RespostaFacade respostaFacade;
	private User usuarioLogado;
	
	public RespostaMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public RespostaFacade getRespostaFacade() {
		if (respostaFacade == null) {
			respostaFacade = new RespostaFacade();
		}

		return respostaFacade;
	}

	public Resposta getResposta() {
		if (resposta == null) {
			resposta = new Resposta();
		}
		
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public String createResposta() {
		try {			
			resposta.setUser(usuarioLogado);

			getRespostaFacade().createResposta(resposta);
			closeDialog();
			displayInfoMessageToUser("Resposta gravada com sucesso!");
			loadRespostas();
			resetResposta();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarResposta.xhtml";
	}

	public void updateResposta() {
		try {
//			resposta.setTelefone(RetiraMascaras.retirar(resposta.getTelefone()));
//			resposta.setCelular(RetiraMascaras.retirar(resposta.getCelular()));
			
			if (resposta.getId() == 0) {
				createResposta();
			} else {
				resposta.setUser(usuarioLogado);
				getRespostaFacade().updateResposta(resposta);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadRespostas();
				resetResposta();

				// retorna para a listagem de Respostas
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("cadastrarRespostas.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteResposta() {
		try {
			getRespostaFacade().deleteResposta(resposta);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadRespostas();
			resetResposta();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteResposta(String id) {
		int idResposta = Integer.parseInt(id);
		resposta = respostaFacade.findResposta(idResposta);
		deleteResposta();
	}

	public List<Resposta> getAllRespostas() {
		if (respostas == null) {
			loadRespostas();
		}

		return respostas;
	}

	private void loadRespostas() {
		respostas = getRespostaFacade().listAll();
	}

	public void resetResposta() {
		resposta = new Resposta();
	}

	public Resposta pesquisaResposta() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int respostaId = id;
		resposta = respostaFacade.findResposta(respostaId);

		return resposta;
	}
}
