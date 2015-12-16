package com.mb;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Reports.RelatorioAuditoria;
import com.facade.AuditoriaFacade;
import com.facade.RespostaFacade;
import com.model.Auditoria;
import com.model.Pergunta;
import com.model.Questionario;
import com.model.Resposta;
import com.model.User;

@RequestScoped
@ManagedBean
public class AuditoriaMB extends AbstractMB implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Auditoria			auditoria;
	private List<Auditoria>		auditorias;
	private AuditoriaFacade		auditoriaFacade;
	private User				usuarioLogado;
	private String				recomendacao;
	private List<Resposta>		respostas;
	private int					currentTab			= 0;
	private boolean podeEditar;

	public boolean podeEditar(Date dataDaResposta){
		if(dataDaResposta == null){
			podeEditar = false;
			return podeEditar;
		}
		
		Calendar hoje = Calendar.getInstance();
		hoje.setTime( new java.util.Date() );
		
		Calendar dataDaRespostaMais1Dia = Calendar.getInstance();
		dataDaRespostaMais1Dia.setTime(dataDaResposta);
		dataDaRespostaMais1Dia.add(Calendar.DAY_OF_MONTH,1);
		
		System.out.println("hoje: " + hoje.getTime());
		System.out.println("hoje+24h: " + dataDaRespostaMais1Dia.getTime());
		System.out.println("Resposta: " + dataDaResposta);
		
		
		//se a data de hoje + 1 dia for maior que a data da resposta não é permitido alterar
		if(hoje.after(dataDaRespostaMais1Dia)){
			podeEditar = false;
		}else{
			podeEditar = true;
		}
		
		System.out.println("pode editar? " + podeEditar);
		return podeEditar;
	}
	
	public boolean isPodeEditar() {
		return podeEditar;
	}

	public void setPodeEditar(boolean podeEditar) {
		this.podeEditar = podeEditar;
	}

	public int getCurrentTab() {
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentTab") != null)
			currentTab = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentTab");
		return currentTab;
	}

	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	public void onTabChange(org.primefaces.event.TabChangeEvent event) {
		String titulo = event.getTab().getTitle();

		List<Questionario> questionarios = new QuestionarioMB().getAllQuestionarios();
		int i = 0;
		for (Questionario questionario : questionarios) {
			if (titulo.equals(questionario.getTitulo())) {
				this.setCurrentTab(i);
			}
			i++;
		}

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().setAttribute("currentTab", currentTab);
	}

	public AuditoriaMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");
	}

	public List<Resposta> pegaRespostas(java.lang.Integer questionario_id) {
		respostas = new ArrayList<Resposta>();

		for (Resposta resposta : auditoria.getRespostas()) {
			if (resposta.getQuestionario().getId() == questionario_id)
				respostas.add(resposta);
		}
		return respostas;
	}

	public User getUsuarioLogado() {
		if (auditoriaFacade == null) {
			auditoriaFacade = new AuditoriaFacade();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(User usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
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
	
	public void visualizarRelatorio(){
		RelatorioAuditoria relat = new RelatorioAuditoria();
		byte[] b = relat.imprimeRelatorio(usuarioLogado.getName(), auditoria.getId());
		
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  
        res.setContentType("application/pdf");  
        //Código abaixo gerar o relatório e disponibiliza diretamente na página   
        res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");  
		//Código abaixo gerar o relatório e disponibiliza para o cliente baixar ou salvar   
        //res.setHeader("Content-disposition", "attachment;filename=arquivo.pdf");  
        try {
			res.getOutputStream().write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        res.getCharacterEncoding();  
        FacesContext.getCurrentInstance().responseComplete();  
        System.out.println("saiu do visualizar relatorio");  
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
			// Só adiciona as perguntas que forem
			// referentes aos tipos de estabelecimento
			if ((pergunta.getTipoServico() != null) && (pergunta.getTipoServico().equals(auditoria.getEstabelecimento().getTipoServico())
					|| pergunta.getTipoServico().equals("Ambos"))) {
				Resposta resposta = new Resposta();
				resposta.setPergunta(pergunta.getPergunta());
				resposta.setHint(pergunta.getHint());
				resposta.setQuestionario(pergunta.getQuestionario());
				resposta.setRecomendacaoPadrao(pergunta.getRecomendacaoPadrao());
				resposta.setTipoServico(pergunta.getTipoServico());
				resposta.setAuditoria(auditoria);
				new RespostaFacade().createResposta(resposta);
			}
		}

		List<Resposta> respostas = new RespostaMB().getAllRespostas();
		auditoria.setRespostas(respostas);
		updateAuditoria();
	}

	public void updateAuditoria() {
		try {
			if (auditoria.getId() == 0) {
				createAuditoria();
			} else {
				auditoria.setUser(usuarioLogado);
				getAuditoriaFacade().updateAuditoria(auditoria);
				closeDialog();
				displayInfoMessageToUser("Atualizado com sucesso!");
				loadAuditorias();
				resetAuditoria();
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
		if(usuarioLogado.isAdmin())
			auditorias = getAuditoriaFacade().listAll();
		else{
			auditorias = new ArrayList<Auditoria>();
			List<Auditoria> todasAuditorias = getAuditoriaFacade().listAll();
			
			for (Auditoria auditoria : todasAuditorias) {
				for (User user : auditoria.getAuditores()) {
					if(user.getId() == usuarioLogado.getId())
						auditorias.add(auditoria);					
				}
			}
		}
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
