package com.mb;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.email.PendenciaAlterada;
import com.email.PendenciaFechada;
import com.facade.IteracaoFacade;
import com.facade.PendenciaFacade;
import com.model.Iteracao;
import com.model.Pendencia;
import com.model.Prioridade;
import com.model.Status;
import com.model.User;

@ViewScoped
@ManagedBean
public class IteracaoMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Iteracao iteracao;
	private List<Iteracao> iteracaos;
	private IteracaoFacade iteracaoFacade;
	private HtmlOutputText pendenciaId;
	private Pendencia pendencia;
	private PendenciaFacade pendenciaFacade;
	HtmlSelectOneMenu selectOne;
	HtmlSelectOneMenu selectOne1;
	
	public HtmlSelectOneMenu getSelectOne1() {
		return selectOne1;
	}

	public void setSelectOne1(HtmlSelectOneMenu selectOne1) {
		this.selectOne1 = selectOne1;
	}

	public HtmlSelectOneMenu getSelectOne() {
		return selectOne;
	}

	public void setSelectOne(HtmlSelectOneMenu selectOne) {
		this.selectOne = selectOne;
	}

	public Pendencia getPendencia() {
		return pendencia;
	}

	public void setPendencia(Pendencia pendencia) {
		this.pendencia = pendencia;
	}

	public void setaPendencia() {
		pendencia = (Pendencia) pendenciaId.getAttributes().get("pendenciaId");
	}

	public HtmlOutputText getPendenciaId() {
		return pendenciaId;
	}

	public void setPendenciaId(HtmlOutputText pendenciaId) {
		this.pendenciaId = pendenciaId;
	}

	public PendenciaFacade getPendenciaFacade() {
		if (pendenciaFacade == null) {
			pendenciaFacade = new PendenciaFacade();
		}
		return pendenciaFacade;
	}

	public IteracaoFacade getIteracaoFacade() {
		if (iteracaoFacade == null) {
			iteracaoFacade = new IteracaoFacade();
		}

		return iteracaoFacade;
	}

	public Iteracao getIteracao() {
		if (iteracao == null) {
			iteracao = new Iteracao();
		}

		return iteracao;
	}

	public void setIteracao(Iteracao iteracao) {
		this.iteracao = iteracao;
	}

	public String createIteracao() {
		try {
			Prioridade prioridadeAnterior;
			Prioridade prioridadeAtual;
			
			User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
			
			if(user == null)
				throw new RuntimeException("Problemas com usuário");

			iteracao.setUsuario(user);
			iteracao.setPendencia(pendencia);

			// Seta a data de criação da iteracao
			iteracao.setDataDaPendencia(new GregorianCalendar());
			
			PendenciaFacade pf = getPendenciaFacade();
			pendencia = pf.findPendencia(iteracao.getPendencia().getId());

			//pega a prioridade atual da pendencia
			prioridadeAnterior = pendencia.getPrioridade();
			//pega a prioridade que será alterada
			prioridadeAtual = iteracao.getPrioridade();
			
			if(iteracao.getStatus()!= null)
				pendencia.setStatus(iteracao.getStatus());
			
			if(iteracao.getCategoria() != null)
				pendencia.setCategoria(iteracao.getCategoria());
			
			if(iteracao.getPrioridade() != null)
				pendencia.setPrioridade(iteracao.getPrioridade());
			
			pf.updatePendencia(pendencia);
			
			getIteracaoFacade().createIteracao(iteracao);
			
			//envia o e-mail
			if(iteracao.getStatus() == Status.FECHADO)
				PendenciaFechada.enviaEmail(iteracao);
			else if(prioridadeAnterior == Prioridade.GRAVE || prioridadeAtual == Prioridade.GRAVE)
				PendenciaAlterada.enviaEmail(iteracao);
			
			closeDialog();
			displayInfoMessageToUser("Registrado com sucesso!");
			loadIteracaos();
			resetIteracao();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
			return "/restrito/erro.xhtml";
		}

		return "";
	}

	public void updateIteracao() {
		try {
			getIteracaoFacade().updateIteracao(iteracao);
			closeDialog();
			displayInfoMessageToUser("Atualizado com sucesso!");
			loadIteracaos();
			resetIteracao();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteIteracao() {
		try {
			getIteracaoFacade().deleteIteracao(iteracao);
			closeDialog();
			displayInfoMessageToUser("Deletado com sucesso!");
			loadIteracaos();
			resetIteracao();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ops, ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	public void deleteIteracao(String id) {
		int idIteracao = Integer.parseInt(id);
		iteracao = iteracaoFacade.findIteracao(idIteracao);
		deleteIteracao();
	}

	public List<Iteracao> getAllIteracoes() {
		if (iteracaos == null) {
			loadIteracaos();
		}

		return iteracaos;
	}

	private void loadIteracaos() {
		iteracaos = getIteracaoFacade().listAll();
	}

	public void resetIteracao() {
		iteracao = new Iteracao();
	}

	public Iteracao pesquisaIteracao() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

		int id = (Integer) session.getAttribute("id");
		int iteracaoId = id;
		iteracao = iteracaoFacade.findIteracao(iteracaoId);

		return iteracao;
	}
}
