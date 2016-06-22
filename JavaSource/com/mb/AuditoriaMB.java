package com.mb;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Reports.RelatorioAuditoria;
import com.facade.AuditoriaFacade;
import com.facade.RespostaFacade;
import com.integracao.ExportaXml;
import com.integracao.ImportaXml;
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

	private static final long serialVersionUID = 1L;

	private Auditoria auditoria;
	private List<Auditoria> auditorias;
	private AuditoriaFacade auditoriaFacade;
	private User usuarioLogado;
	private String recomendacao;
	private List<Resposta> respostas;
	private int currentTab = 0;
	private boolean podeEditar;
	private int quantidadeDePerguntasNaoRespondidas = 0;
	private int quantidadeDePerguntasRespondidas = 0;
	private FacesContext context;
	private HttpServletRequest request;
	private boolean offLine;
	private Parametros parametros;

	public AuditoriaMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usuário");

		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		setOffLine(bundle.getString("modulo").equals("off"));
	}

	public boolean isOffLine() {
		return offLine;
	}

	public void setOffLine(boolean offLine) {
		this.offLine = offLine;
	}

	public boolean podeEditar(Date dataDaResposta) {
		if (dataDaResposta == null) {
			podeEditar = false;
			return podeEditar;
		}

		parametros = (Parametros) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("parametros");

		Calendar hoje = Calendar.getInstance();
		hoje.setTime(new java.util.Date());

		Calendar prazoParaEditarResposta = Calendar.getInstance();
		prazoParaEditarResposta.setTime(dataDaResposta); // recebe a data da
															// resposta no
															// formato Date e
															// converte para
															// Calendar
		prazoParaEditarResposta.add(Calendar.DAY_OF_MONTH,
				parametros.getQuantidadeDeDiasParaAlterarAResposta());

		// se a data de hoje + XXX dia for maior que a data da resposta não é
		// permitido alterar
		if (hoje.after(prazoParaEditarResposta)) {
			podeEditar = false;
		} else {
			podeEditar = true;
		}
		return podeEditar;
	}

	private void contaPerguntas() {
		quantidadeDePerguntasNaoRespondidas = 0;
		quantidadeDePerguntasRespondidas = 0;

		if (auditoria != null && auditoria.getRespostas() != null) {
			for (Resposta resposta : auditoria.getRespostas()) {
				if (resposta.getResposta() == null) {
					if (usuarioLogado.isAdmin())
						quantidadeDePerguntasNaoRespondidas++;
					else {
						for (Questionario q : usuarioLogado.getQuestionarios()) {
							if (resposta.getQuestionario().getId() == q.getId())
								quantidadeDePerguntasNaoRespondidas++;
						}
					}
				} else {
					quantidadeDePerguntasRespondidas++;
				}
			}
		}
	}

	public int getQuantidadeDePerguntasNaoRespondidas() {
		contaPerguntas();
		return quantidadeDePerguntasNaoRespondidas;
	}

	public void setQuantidadeDePerguntasNaoRespondidas(
			int quantidadeDePerguntasNaoRespondidas) {
		this.quantidadeDePerguntasNaoRespondidas = quantidadeDePerguntasNaoRespondidas;
	}

	public int getQuantidadeDePerguntasRespondidas() {
		contaPerguntas();
		return quantidadeDePerguntasRespondidas;
	}

	public void setQuantidadeDePerguntasRespondidas(
			int quantidadeDePerguntasRespondidas) {
		this.quantidadeDePerguntasRespondidas = quantidadeDePerguntasRespondidas;
	}

	public boolean isPodeEditar() {
		return podeEditar;
	}

	public void setPodeEditar(boolean podeEditar) {
		this.podeEditar = podeEditar;
	}

	public boolean exibeTab(int quesstionario_id) {
		List<Resposta> r = pegaRespostas(quesstionario_id);

		if (r != null && r.size() > 0)
			return false;

		return true;
	}

	public int getCurrentTab() {
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("currentTab") != null)
			currentTab = (Integer) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("currentTab");
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
		request = (HttpServletRequest) context.getExternalContext()
				.getRequest();
		request.getSession().setAttribute("currentTab", currentTab);
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

	public void visualizarRelatorio() {
		RelatorioAuditoria relat = new RelatorioAuditoria();
		byte[] b = relat.imprimeRelatorio(usuarioLogado.getName(),
				auditoria.getId(), auditoria.getEstabelecimento().getId());

		HttpServletResponse res = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		res.setContentType("application/pdf");
		// Código abaixo gerar o relatório e disponibiliza diretamente na página
		res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");
		// Código abaixo gerar o relatório e disponibiliza para o cliente baixar
		// ou salvar
		// res.setHeader("Content-disposition",
		// "attachment;filename=arquivo.pdf");
		try {
			res.getOutputStream().write(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		res.getCharacterEncoding();
		FacesContext.getCurrentInstance().responseComplete();
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
			if ((pergunta.getTipoServico() != null)
					&& (pergunta.getTipoServico().equals(
							auditoria.getEstabelecimento().getTipoServico()
									.substring(0, 2)) || pergunta
							.getTipoServico().equals("Ambos"))) {

				if (idQuestionarioAnterior != pergunta.getQuestionario()
						.getId()) {
					new RespostaFacade()
							.createResposta(incluiPerguntaNomeDoResponsavel(pergunta
									.getQuestionario()));
					new RespostaFacade()
							.createResposta(incluiPerguntaObservacao(pergunta
									.getQuestionario()));
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

	private Resposta incluiPerguntaNomeDoResponsavel(Questionario q) {
		Resposta resposta = new Resposta();
		resposta.setPergunta("RESPONSÁVEL");
		resposta.setHint("PESSOA RESPONSÁVEL POR FORNECER AS RESPOSTAS PARA ESTE QUESTIONÁRIO");
		resposta.setQuestionario(q);
		resposta.setRecomendacaoPadrao(null);
		resposta.setTipoDeResposta(Flag.A_RESPONSAVEL);
		resposta.setAuditoria(auditoria);
		return resposta;
	}

	private Resposta incluiPerguntaObservacao(Questionario q) {
		Resposta resposta = new Resposta();
		resposta.setPergunta("CONSIDERAÇÕES FINAIS");
		resposta.setHint("CONSIDERAÇÕES FINAIS PARA ESTE QUESTIONÁRIO");
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
			displayInfoMessageToUser("Auditoria " + auditoria.getCodigo()
					+ " deletada com sucesso!");
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

	public List<Auditoria> getAllAuditoriasOff() {
		if (auditorias == null) {
			auditorias = getAuditoriaFacade().listAuditoriasOff();
		}

		return auditorias;
	}

	private void loadAuditorias() {
		if (usuarioLogado.isAdmin())
			if (isOffLine())
				auditorias = getAuditoriaFacade().listAuditoriasOff();
			else
				auditorias = getAuditoriaFacade().listAll();
		else {
			auditorias = new ArrayList<Auditoria>();
			List<Auditoria> todasAuditorias;

			if (isOffLine())
				todasAuditorias = getAuditoriaFacade().listAuditoriasOff();
			else
				todasAuditorias = getAuditoriaFacade().listAll();

			for (Auditoria auditoria : todasAuditorias) {
				for (User user : auditoria.getAuditores()) {
					if (user.getId() == usuarioLogado.getId())
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
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);

		int id = (Integer) session.getAttribute("id");
		int auditoriaId = id;
		auditoria = auditoriaFacade.findAuditoria(auditoriaId);

		return auditoria;
	}

	public Date getHoje() {
		return new Date();
	}

	public void geraArquivosXml() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext scontext = (ServletContext) facesContext
				.getExternalContext().getContext();
		String path = scontext
				.getRealPath("/WEB-INF/IntegracaoAuditoria/auditoriaId");

		File diretorio = new File(path + auditoria.getId());
		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}

		String caminho = diretorio.getPath() + "/";
		ExportaXml xml = new ExportaXml(caminho);
		xml.exporta(auditoria);
		
		copiaArquivosParaServidor(diretorio.getPath());
	}

	private boolean leArquivosXml() {
		ImportaXml xml = new ImportaXml();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext scontext = (ServletContext) facesContext
				.getExternalContext().getContext();
		String path = scontext
				.getRealPath("/WEB-INF/IntegracaoAuditoria/auditoriaId"
						+ auditoria.getId());

		File dir = new File(path);

		File diretorio = new File(dir.getPath());
		File fList[] = diretorio.listFiles();

		if (fList == null) {
			return false;
		}

		for (int i = 0; i < fList.length; i++) {
			if (fList[i].isFile()) {
				if (fList[i].getName().contains("estabelecimento"))
					xml.importaEstabelecimentoParaServidor(fList[i]
							.getAbsolutePath());

				if (fList[i].getName().contains("respostas"))
					xml.importaRespostaParaServidor(fList[i].getAbsolutePath());
			}
		}

		return true;
	}

	public String importaDadosDoLocalNoServidor() {
		if (leArquivosXml()) {
			closeDialog();
			displayInfoMessageToUser("Auditoria importada com sucesso!");
		} else {
			closeDialog();
			displayErrorMessageToUser("Não há arquivos para ser importado!");
		}
		
		return "/restrito/importaDoLocalParaOServidor.xhtml";
	}

	public String enviaDadosParaOServidor() {
		geraArquivosXml();

		closeDialog();
		displayInfoMessageToUser("Auditoria exportada com sucesso!");
		
		return "/restrito/home.xhtml";
	}

	public void copiaArquivosParaServidor(String caminhoOrigem){
		File origem = new File(caminhoOrigem);
		File destino = new File("\\\\200.198.51.90\\teste\\" + origem.getName());
		if(!destino.exists())
			destino.mkdirs();
			
		String comando = "xcopy \"" + caminhoOrigem + "\" "+ destino.getPath() + " /Y /S /E";
		System.out.println("Executando: " + comando);
		
		Runtime runtime = Runtime.getRuntime();
		try {
			Process p = runtime.exec(comando);
			p.waitFor();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("fim");
	}

	public String importaDoServidorParaLocal() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext scontext = (ServletContext) facesContext
				.getExternalContext().getContext();
		File path = new File(scontext.getRealPath("/WEB-INF/Backup"));

		Process p;
		Runtime runtime = Runtime.getRuntime();
		try {
			p = runtime.exec("cmd /c \"" + path.getPath() 
					+ "\\1_backup.bat\"");
			p.waitFor();
			p = runtime.exec("cmd /c \"" + path.getPath()
					+ "\\2_createBD.bat\"");
			p.waitFor();
			p = runtime.exec("cmd /c \"" + path.getPath()
					+ "\\3_restoreBD.bat\"");
			p.waitFor();
			closeDialog();
			displayInfoMessageToUser("Atualizado com sucesso!");
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
			closeDialog();
			displayErrorMessageToUser("Aconteceu algum erro!\nFavor contactar o administrador do sistema.");
		} catch (InterruptedException e) {
			System.out.println("InterruptedException");
			closeDialog();
			displayErrorMessageToUser("Aconteceu algum erro!\nFavor contactar o administrador do sistema.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
			closeDialog();
			displayErrorMessageToUser("Aconteceu algum erro!\nFavor contactar o administrador do sistema.");
		}

		return "/restrito/home.xhtml";
	}
}
