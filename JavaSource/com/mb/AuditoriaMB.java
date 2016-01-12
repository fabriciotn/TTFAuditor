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
import com.facade.ParametrosFacade;
import com.facade.RespostaFacade;
import com.model.Auditoria;
import com.model.Flag;
import com.model.Parametros;
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
	private int quantidadeDePerguntasNaoRespondidas = 0;

	private FacesContext context;

	private HttpServletRequest request;

	public boolean podeEditar(Date dataDaResposta){
		if(dataDaResposta == null){
			podeEditar = false;
			return podeEditar;
		}
		
		Parametros parametros = new ParametrosFacade().findParametros(1);
		
		Calendar hoje = Calendar.getInstance();
		hoje.setTime( new java.util.Date() );
		
		Calendar prazoParaEditarResposta = Calendar.getInstance();
		prazoParaEditarResposta.setTime(dataDaResposta); //recebe a data da resposta no formato Date e converte para Calendar
		prazoParaEditarResposta.add(Calendar.DAY_OF_MONTH,parametros.getQuantidadeDeDiasParaAlterarAResposta());

		//se a data de hoje + XXX dia for maior que a data da resposta não é permitido alterar
		if(hoje.after(prazoParaEditarResposta)){
			podeEditar = false;
		}else{
			podeEditar = true;
		}
		return podeEditar;
	}
	
	public int getQuantidadeDePerguntasNaoRespondidas() {
		quantidadeDePerguntasNaoRespondidas = 0;
		for (Resposta resposta : auditoria.getRespostas()) {
			if(resposta.getResposta() == null){
				if(usuarioLogado.isAdmin())
					quantidadeDePerguntasNaoRespondidas++;
				else{
					for (Questionario q : usuarioLogado.getQuestionarios()) {
						if(resposta.getQuestionario().getId() == q.getId())
							quantidadeDePerguntasNaoRespondidas++;
					}
				}
			}
		}
		return quantidadeDePerguntasNaoRespondidas;
	}

	public void setQuantidadeDePerguntasNaoRespondidas(int quantidadeDePerguntasNaoRespondidas) {
		this.quantidadeDePerguntasNaoRespondidas = quantidadeDePerguntasNaoRespondidas;
	}

	public boolean isPodeEditar() {
		return podeEditar;
	}

	public void setPodeEditar(boolean podeEditar) {
		this.podeEditar = podeEditar;
	}

	public boolean exibeTab(int quesstionario_id) {
		List<Resposta> r = pegaRespostas(quesstionario_id);
		
		if(r != null && r.size() > 0)
			return true;
		
		return false;
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

		List<Questionario> questionarios;
		
		if (usuarioLogado.isAdmin())
			questionarios = new QuestionarioMB().getAllQuestionarios();
		else
			questionarios = usuarioLogado.getQuestionarios();
		
		int i = 0;
		for (Questionario questionario : questionarios) {
			if (titulo.equals(questionario.getTitulo())) {
				this.setCurrentTab(i);
			}
			i++;
		}

		context = FacesContext.getCurrentInstance();
		request = (HttpServletRequest) context.getExternalContext().getRequest();
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
		byte[] b = relat.imprimeRelatorio(usuarioLogado.getName(), auditoria.getId(), auditoria.getEstabelecimento().getId());
		
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
		int idQuestionarioAnterior = 0;

		for (Pergunta pergunta : perguntas) {
			// Só adiciona as perguntas que forem
			// referentes aos tipos de estabelecimento
			if ((pergunta.getTipoServico() != null) && (pergunta.getTipoServico().equals(auditoria.getEstabelecimento().getTipoServico())
					|| pergunta.getTipoServico().equals("Ambos"))) {
				
				
				if(idQuestionarioAnterior != pergunta.getQuestionario().getId()){
					new RespostaFacade().createResposta(incluiPerguntaNomeDoResponsavel(pergunta.getQuestionario()));
					new RespostaFacade().createResposta(incluiPerguntaObservacao(pergunta.getQuestionario()));
					idQuestionarioAnterior = pergunta.getQuestionario().getId();
				}
				
				Resposta resposta = new Resposta();
				resposta.setPergunta(pergunta.getPergunta());
				resposta.setHint(pergunta.getHint());
				resposta.setQuestionario(pergunta.getQuestionario());
				resposta.setRecomendacaoPadrao(pergunta.getRecomendacaoPadrao());
				resposta.setTipoServico(pergunta.getTipoServico());
				resposta.setTipoDeResposta(Flag.B_PERGUNTA);
				resposta.setAuditoria(auditoria);
				new RespostaFacade().createResposta(resposta);
			}
		}

		List<Resposta> respostas = new RespostaMB().getAllRespostas();
		auditoria.setRespostas(respostas);
		updateAuditoria();
	}
	
	private Resposta incluiPerguntaNomeDoResponsavel(Questionario q){
		Resposta resposta = new Resposta();
		resposta.setPergunta("RESPONSÁVEL");
		resposta.setHint("PESSOA RESPONSÁVEL POR FORNECER AS RESPOSTAS PARA ESTE QUESTIONÁRIO");
		resposta.setQuestionario(q);
		resposta.setRecomendacaoPadrao(null);
		resposta.setTipoDeResposta(Flag.A_RESPONSAVEL);
		resposta.setAuditoria(auditoria);
		return resposta;
	}
	
	private Resposta incluiPerguntaObservacao(Questionario q){
		Resposta resposta = new Resposta();
		resposta.setPergunta("OBSERVAÇÃO");
		resposta.setHint("OBSERVAÇÕES PARA ESTE QUESTIONÁRIO");
		resposta.setQuestionario(q);
		resposta.setRecomendacaoPadrao(null);
		resposta.setTipoDeResposta(Flag.C_OBSERVACAO);
		resposta.setAuditoria(auditoria);
		return resposta;
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
