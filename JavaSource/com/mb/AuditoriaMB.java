package com.mb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.AuditoriaFacade;
import com.facade.RespostaFacade;
import com.model.Auditoria;
import com.model.Pergunta;
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
	private String recomendacao;

	public AuditoriaMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}
	
	public String getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(String recomendacao) {
		this.recomendacao = recomendacao;
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

	public String createAuditoria() {
		try {
			auditoria.setUser(usuarioLogado);

			getAuditoriaFacade().createAuditoria(auditoria);
			adicionaPerguntas(auditoria);
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
	
	private void adicionaPerguntas(Auditoria auditoria) {
		List<Pergunta> perguntas = new PerguntaMB().getAllPerguntas();
		
		for (Pergunta pergunta : perguntas) {
			Resposta resposta = new Resposta();
			resposta.setPergunta(pergunta.getPergunta());
			resposta.setHint(pergunta.getHint());
			resposta.setQuestionario(pergunta.getQuestionario());
			resposta.setAuditoria(auditoria);
			new RespostaFacade().createResposta(resposta);
		}
		
		List<Resposta> respostas = new RespostaMB().getAllRespostas();
		auditoria.setRespostas(respostas);
		updateAuditoria();
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
				context.redirect("cadastrarAuditorias.xhtml");
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

}
