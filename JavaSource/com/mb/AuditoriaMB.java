package com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.facade.AuditoriaFacade;
import com.facade.PerguntaFacade;
import com.facade.QuestionarioFacade;
import com.facade.RespostaFacade;
import com.model.Auditoria;
import com.model.Pergunta;
import com.model.Questionario;
import com.model.Resposta;
import com.model.User;

@RequestScoped
@ManagedBean
public class AuditoriaMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;
	private List<Auditoria> auditorias;
	private AuditoriaFacade auditoriaFacade;
	private User usuarioLogado;

	private Questionario questionario;
	private List<Questionario> questionarios;
	private QuestionarioFacade questionarioFacade;

	private Pergunta pergunta;
	private List<Pergunta> perguntas;
	private PerguntaFacade perguntaFacade;

	private Resposta resposta;
	private List<Resposta> respostas;
	private RespostaFacade respostaFacade;

	public AuditoriaMB() {
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

	public List<Questionario> getAllQuestionarios() {
		if (questionarios == null) {
			loadQuestionarios();
		}

		return questionarios;
	}

	private void loadQuestionarios() {
		questionarios = getQuestionarioFacade().findQuestionarios(auditoria.getId());
	}

	public void resetQuestionario() {
		questionario = new Questionario();
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

	public List<Pergunta> getAllPerguntas() {
		if (perguntas == null) {
			loadPerguntas();
		}

		return perguntas;
	}

	private void loadPerguntas() {
		perguntas = getPerguntaFacade().findPerguntas(questionario.getId());
	}

	public void resetPergunta() {
		pergunta = new Pergunta();
	}

	public AuditoriaFacade getAuditoriaFacade() {
		if (auditoriaFacade == null) {
			auditoriaFacade = new AuditoriaFacade();
		}

		return auditoriaFacade;
	}

	public Auditoria getAuditoria() {
		if (auditoria == null) {
			auditoria = new Auditoria();
		}

		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
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

	public String createAuditoria() {
		try {
			auditoria.setUser(usuarioLogado);

			getAuditoriaFacade().createAuditoria(auditoria);
			closeDialog();
			displayInfoMessageToUser("Auditoria gravada com sucesso!");
			loadAuditorias();
			resetAuditoria();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}
		return "/restrito/cadastrarAuditoria.xhtml";
	}

	public void updateAuditoria() {
		try {
			// auditoria.setTelefone(RetiraMascaras.retirar(auditoria.getTelefone()));
			// auditoria.setCelular(RetiraMascaras.retirar(auditoria.getCelular()));

			if (auditoria.getId() == 0) {
				createAuditoria();
			} else {
				auditoria.setUser(usuarioLogado);
				getAuditoriaFacade().updateAuditoria(auditoria);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadAuditorias();
				resetAuditoria();

				// retorna para a listagem de Auditorias
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				context.redirect("gerenciarAuditorias.xhtml");
			}
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteAuditoria() {
		try {
			getAuditoriaFacade().deleteAuditoria(auditoria);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadAuditorias();
			resetAuditoria();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void realizaAuditoria(ActionEvent actionEvent) {
		System.out.println("teste1");
	}

	public void deleteAuditoria(String id) {
		int idAuditoria = Integer.parseInt(id);
		auditoria = auditoriaFacade.findAuditoria(idAuditoria);
		deleteAuditoria();
	}

	public List<Auditoria> getAllAuditorias() {
		if (auditorias == null) {
			loadAuditorias();
		}

		return auditorias;
	}

	private void loadAuditorias() {
		auditorias = getAuditoriaFacade().listAll();
	}

	public void resetAuditoria() {
		auditoria = new Auditoria();
	}

	public Auditoria pesquisaAuditoria() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int auditoriaId = id;
		auditoria = auditoriaFacade.findAuditoria(auditoriaId);

		return auditoria;
	}

	public Date getHoje() {
		return new Date();
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
	
	public void session(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().setAttribute("auditoria", auditoria);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect("teste1.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
