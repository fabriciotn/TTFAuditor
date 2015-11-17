package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.QuestionarioFacade;
import com.model.Questionario;
import com.model.User;

@RequestScoped
@ManagedBean
public class QuestionarioMB extends AbstractMB implements Serializable {
/*
	private static final long serialVersionUID = 1L;

	private Questionario questionario;
	private List<Questionario> questionarios;
	private QuestionarioFacade questionarioFacade;
	private User usuarioLogado;
	
	public QuestionarioMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public QuestionarioFacade getQuestionarioFacade() {
		if (questionarioFacade == null) {
			questionarioFacade = new QuestionarioFacade();
		}

		return questionarioFacade;
	}

	public Questionario getQuestionario() {
		if (questionario == null) {
			questionario = new Questionario();
		}

		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public String createQuestionario() {
		try {
			questionario.setUser(usuarioLogado);

			getQuestionarioFacade().createQuestionario(questionario);
			closeDialog();
			displayInfoMessageToUser("Questionario " + questionario.getTitulo() +" gravado com sucesso!");
			loadQuestionarios();
			resetQuestionario();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarQuestionario.xhtml";
	}

	public void updateQuestionario() {
		try {
//			questionario.setTelefone(RetiraMascaras.retirar(questionario.getTelefone()));
//			questionario.setCelular(RetiraMascaras.retirar(questionario.getCelular()));
			
			if (questionario.getId() == 0) {
				createQuestionario();
			} else {
				questionario.setUser(usuarioLogado);
				getQuestionarioFacade().updateQuestionario(questionario);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadQuestionarios();
				resetQuestionario();

				// retorna para a listagem de Questionarios
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("cadastrarQuestionarios.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteQuestionario() {
		try {
			getQuestionarioFacade().deleteQuestionario(questionario);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadQuestionarios();
			resetQuestionario();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteQuestionario(String id) {
		int idQuestionario = Integer.parseInt(id);
		questionario = questionarioFacade.findQuestionario(idQuestionario);
		deleteQuestionario();
	}

	public List<Questionario> getAllQuestionarios() {
		if (questionarios == null) {
			loadQuestionarios();
		}

		return questionarios;
	}

	private void loadQuestionarios() {
		questionarios = getQuestionarioFacade().listAll();
	}

	public void resetQuestionario() {
		questionario = new Questionario();
	}

	public Questionario pesquisaQuestionario() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int questionarioId = id;
		questionario = questionarioFacade.findQuestionario(questionarioId);

		return questionario;
	}
	*/
}
