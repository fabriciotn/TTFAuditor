package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.PerguntaFacade;
import com.model.Pergunta;
import com.model.User;

@RequestScoped
@ManagedBean
public class PerguntaMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pergunta pergunta;
	private List<Pergunta> perguntas;
	private PerguntaFacade perguntaFacade;
	private User usuarioLogado;
	
	public PerguntaMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public PerguntaFacade getPerguntaFacade() {
		if (perguntaFacade == null) {
			perguntaFacade = new PerguntaFacade();
		}

		return perguntaFacade;
	}

	public Pergunta getPergunta() {
		if (pergunta == null) {
			pergunta = new Pergunta();
		}
		
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public String createPergunta() {
		try {
			pergunta.setUser(usuarioLogado);

			getPerguntaFacade().createPergunta(pergunta);
			closeDialog();
			displayInfoMessageToUser("Pergunta gravada com sucesso!");
			loadPerguntas();
			resetPergunta();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarPergunta.xhtml";
	}

	public void updatePergunta() {
		try {
//			pergunta.setTelefone(RetiraMascaras.retirar(pergunta.getTelefone()));
//			pergunta.setCelular(RetiraMascaras.retirar(pergunta.getCelular()));
			
			if (pergunta.getId() == 0) {
				createPergunta();
			} else {
				pergunta.setUser(usuarioLogado);
				getPerguntaFacade().updatePergunta(pergunta);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadPerguntas();
				resetPergunta();

				// retorna para a listagem de Perguntas
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("cadastrarPerguntas.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deletePergunta() {
		try {
			getPerguntaFacade().deletePergunta(pergunta);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadPerguntas();
			resetPergunta();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deletePergunta(String id) {
		int idPergunta = Integer.parseInt(id);
		pergunta = perguntaFacade.findPergunta(idPergunta);
		deletePergunta();
	}

	public List<Pergunta> getAllPerguntas() {
		if (perguntas == null) {
			loadPerguntas();
		}

		return perguntas;
	}

	private void loadPerguntas() {
		perguntas = getPerguntaFacade().listAll();
	}

	public void resetPergunta() {
		pergunta = new Pergunta();
	}

	public Pergunta pesquisaPergunta() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int perguntaId = id;
		pergunta = perguntaFacade.findPergunta(perguntaId);

		return pergunta;
	}
}
