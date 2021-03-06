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

/**
 * Managed Bean para gest�o das Auditorias
 * @author TTF Inform�tica
 *
 */
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

	/**
	 * Construtor
	 */
	public AuditoriaMB() {
		usuarioLogado = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");

		if (usuarioLogado == null)
			throw new RuntimeException("Problemas com usu�rio");

		ResourceBundle bundle = ResourceBundle.getBundle("messages");
		setOffLine(bundle.getString("modulo").equals("off"));
	}

	public boolean isOffLine() {
		return offLine;
	}

	public void setOffLine(boolean offLine) {
		this.offLine = offLine;
	}

	/**
	 * M�todo respons�vel por verificar se uma pergunta pode ser alterada,
	 * de acordo com a data em rela��o ao par�metro.
	 * @param dataDaResposta
	 * @return true or false
	 */
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

		// se a data de hoje + XXX dia for maior que a data da resposta n�o �
		// permitido alterar
		if (hoje.after(prazoParaEditarResposta)) {
			podeEditar = false;
		} else {
			podeEditar = true;
		}
		return podeEditar;
	}

	/**
	 * Conta perguntas respondidas e n�o respondidas
	 */
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

	/**
	 * M�todo para retornar a aba corrente
	 * @return currentTab
	 */
	public int getCurrentTab() {
		if (FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("currentTab") != null)
			currentTab = (Integer) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("currentTab");
		return currentTab;
	}

	/**
	 * Seta a aba corrente
	 */
	public void setCurrentTab(int currentTab) {
		this.currentTab = currentTab;
	}

	/**
	 * M�todo para controlar a mudan�a de abas
	 * @param event
	 */
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

	/**
	 * M�todo para buscar resposatas de uma auditoria, buscando pelo id do question�rio
	 * @param questionario_id
	 * @return listaDeResposta
	 */
	public List<Resposta> pegaRespostas(java.lang.Integer questionario_id) {
		respostas = new ArrayList<Resposta>();

		for (Resposta resposta : auditoria.getRespostas()) {
			if (resposta.getQuestionario().getId() == questionario_id)
				respostas.add(resposta);
		}
		return respostas;
	}

	/**
	 * Busca usu�rio logado
	 * @return
	 */
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

	/**
	 * Gera o relat�rio de Auditorias
	 */
	public void visualizarRelatorio() {
		RelatorioAuditoria relat = new RelatorioAuditoria();
		byte[] b = relat.imprimeRelatorio(usuarioLogado.getName(),
				auditoria.getId(), auditoria.getEstabelecimento().getId());

		HttpServletResponse res = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		res.setContentType("application/pdf");
		// C�digo abaixo gerar o relat�rio e disponibiliza diretamente na p�gina
		res.setHeader("Content-disposition", "inline;filename=arquivo.pdf");
		// C�digo abaixo gerar o relat�rio e disponibiliza para o cliente baixar
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

	/**
	 * Cria auditoria
	 * @return paginaDeSucessoOuErro
	 */
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

	/**
	 * M�todo para adicionar perguntas � auditoria
	 * @param auditoria
	 */
	private void adicionaPerguntas(Auditoria auditoria) {
		List<Pergunta> perguntas = new PerguntaMB().getAllPerguntas();
		int idQuestionarioAnterior = 0;

		for (Pergunta pergunta : perguntas) {
			// S� adiciona as perguntas que forem
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
				resposta.setIdDaPerguntaOriginal(pergunta.getId());
				resposta.setAuditoria(auditoria);
				new RespostaFacade().createResposta(resposta);
			}
		}

		List<Resposta> respostas = new RespostaMB().getAllRespostas();
		auditoria.setRespostas(respostas);
		updateAuditoria();
	}

	/**
	 * M�todo para incluir pergunta "Respons�vel" � auditoria
	 * @param questionario
	 * @return resposta
	 */
	private Resposta incluiPerguntaNomeDoResponsavel(Questionario q) {
		Resposta resposta = new Resposta();
		resposta.setPergunta("RESPONS�VEL");
		resposta.setHint("PESSOA RESPONS�VEL POR FORNECER AS RESPOSTAS PARA ESTE QUESTION�RIO");
		resposta.setQuestionario(q);
		resposta.setRecomendacaoPadrao(null);
		resposta.setTipoDeResposta(Flag.A_RESPONSAVEL);
		resposta.setAuditoria(auditoria);
		return resposta;
	}

	/**
	 * M�todo para incluir pergunta "Observa��o" � auditoria
	 * @param questionario
	 * @return resposta
	 */
	private Resposta incluiPerguntaObservacao(Questionario q) {
		Resposta resposta = new Resposta();
		resposta.setPergunta("CONSIDERA��ES FINAIS");
		resposta.setHint("CONSIDERA��ES FINAIS PARA ESTE QUESTION�RIO");
		resposta.setQuestionario(q);
		resposta.setRecomendacaoPadrao(null);
		resposta.setTipoDeResposta(Flag.C_OBSERVACAO);
		resposta.setAuditoria(auditoria);
		return resposta;
	}

	/**
	 * Atualiza auditoria
	 */
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
	
	/**
	 * Atualiza Observa��es da Auditoria
	 */
	public void updateObservacaoesAuditoria() {
		try {
			auditoria.setUser(usuarioLogado);
			getAuditoriaFacade().updateObservacaoAuditoria(auditoria);
			closeDialog();
			displayInfoMessageToUser("Atualizado com sucesso!");
			loadAuditorias();
			resetAuditoria();

		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
	}

	/**
	 * Deleta auditoria
	 */
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

	/**
	 * Deleta auditoria de acordo com o id
	 * @param id
	 */
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

	/**
	 * Carrega auditorias
	 */
	private void loadAuditorias() {
		if (usuarioLogado.isAdmin())
			if (isOffLine())
				auditorias = getAuditoriaFacade().listAuditoriasOff();
			else
				auditorias = getAuditoriaFacade().listAll();
		else if(usuarioLogado.isAuditor()) {
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
		}else if(usuarioLogado.isGestor()){
			if(isOffLine())
				auditorias = getAuditoriaFacade().listAuditoriasOffPorUnidade(usuarioLogado.getUnidade());
			else
				auditorias = getAuditoriaFacade().listAuditoriasPorUnidade(usuarioLogado.getUnidade());
		}
	}

	public void resetAuditoria() {
		auditoria = new Auditoria();
	}
	
	/**
	 * Inclui auditoria na sess�o
	 * @param auditoria_id
	 * @return auditoria
	 */
	public Auditoria colocaAuditoriaMBNaSessao(int auditoria_id){
		if(auditoria == null && auditoria_id != 0){
			auditoria = getAuditoriaFacade().findAuditoria(auditoria_id);
		}
		return auditoria;
	}

	/**
	 * Pesquisa auditoria
	 * @return
	 */
	public Auditoria pesquisaAuditoria() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext()
				.getSession(true);

		int id = (Integer) session.getAttribute("id");
		int auditoriaId = id;
		auditoria = auditoriaFacade.findAuditoria(auditoriaId);

		return auditoria;
	}

	/**
	 * Pega data de hoje
	 * @return dataDeHoje
	 */
	public Date getHoje() {
		return new Date();
	}

	/**
	 * Chama o m�todo para gera��o dos arquivos XML
	 */
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

	/**
	 * Chama os m�todos para leitura dos arquivos XML
	 * @return
	 */
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

	/**
	 * Em um sistema configurado como m�dulo On-line, 
	 * este m�todo recebe os dados do notebook e realiza a atualiza��o no servidor
	 * @return paginaASerExibida
	 */
	public String importaDadosDoLocalNoServidor() {
		if (leArquivosXml()) {
			closeDialog();
			displayInfoMessageToUser("Auditoria importada com sucesso!");
		} else {
			closeDialog();
			displayErrorMessageToUser("N�o h� arquivos para ser importado!");
		}
		
		return "/restrito/importaDoLocalParaOServidor.xhtml";
	}

	/**
	 * Em um sistema configurado como m�dulo Off-line,
	 * este m�todo envia os dados para o servidor
	 * @return
	 */
	public String enviaDadosParaOServidor() {
		geraArquivosXml();

		closeDialog();
		displayInfoMessageToUser("Auditoria exportada com sucesso!");
		
		return "/restrito/home.xhtml";
	}

	/**
	 * M�todo para realizar a c�pia dos arquivos XML do notebook para o servidor
	 * ATEN��O: Sempre lembrar de atualizar o camiho do diret�rio de recebimento dos dados.
	 * Caso seja necess�rio mudar o servidor, este caminho deve ser alterado.
	 * Em caso de dificuldades de envio/recebimento,
	 * deve ser verificado as permiss�es na pasta Integra��oAuditoria dento do servidor.
	 * M�todo pode quebrar caso a senha seja alterada
	 * @param caminhoOrigem
	 */
	public void copiaArquivosParaServidor(String caminhoOrigem){
		
		String deletaMapeamento = "NET USE J: /DELETE /YES";
		String mapeiaJSemUsuarioDeRede = "NET USE J: \\\\200.198.51.90\\IntegracaoAuditoria";// /USER:hemominas\\sistemas \"H&m0$i$@2011\"";
		String mapeiaJComUsuarioDeRede = "NET USE J: \\\\200.198.51.90\\IntegracaoAuditoria /USER:hemominas\\sistemas \"H&m0$i$@2011\"";
		
		executaComandoDOS(deletaMapeamento);
		if(executaComandoDOS(mapeiaJComUsuarioDeRede) != 0){
			if(executaComandoDOS(mapeiaJSemUsuarioDeRede) != 0)
				throw new RuntimeException("Problema ao mapear a unidade de rede no servidor!");
		}
		
		File origem = new File(caminhoOrigem);
		File destino = new File("J:\\" + origem.getName());
		if(!destino.exists())
			if(destino.mkdirs())
				System.out.println("Caminho criado!");
			else
				throw new RuntimeException("Problema ao criar o caminho no servidor!");
		
		String copiaArquivosParaServidor = "xcopy \"" + caminhoOrigem + "\" "+ destino.getPath() + " /Y /S /E";
		executaComandoDOS(copiaArquivosParaServidor);
		executaComandoDOS(deletaMapeamento);
		
		System.out.println("fim");
		moveArquivo(origem);
	}
	
	/**
	 * Troca os arquivos de pasta ap�s realizar a leitura
	 * @param file
	 */
	public void moveArquivo(File file){
		String fullPath = file.getAbsolutePath().replace(file.getName(), "") + "\\exportados";
		File newDir = new File(fullPath);
		if(!(newDir.exists()) && newDir.mkdir()){
			System.out.println("Criou pasta " + fullPath );
		}
		
		String currentPath = file.getPath().replace("\\" + file.getName(), "");
		File file2 = new File(currentPath + "\\exportados", file.getName());
		if(file.renameTo(file2)){
			System.out.println("Importou: " + currentPath + "\\exportados\\" + file.getName());
		}else{
			System.out.println("N�O Importou: " + currentPath + "\\exportados\\" + file.getName());
			File[] listFiles = file.listFiles();
			for (File arquivo : listFiles) {
				if(arquivo.renameTo(new File(file2+"\\"+arquivo.getName()))){
					System.out.println("Importou: " + file2+"\\"+arquivo.getName());
				}else{
					System.out.println("N�O Importou: " + file2+"\\"+arquivo.getName());
				}
			}
			
			file.delete();
		}
	}

	/**
	 * Em um sistema configurado como m�dulo Off-line,
	 * realiza a chamada das Bat's locais que atualizam a base do notebook.
	 * @return
	 */
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
	
	/**
	 * M�todo para executar comandos do MS-DOS
	 * @param comando
	 * @return sucesso
	 */
	private int executaComandoDOS(String comando){
		System.out.println("Executando comando: " + comando);
		
		Runtime runtime = Runtime.getRuntime();
		int retorno = -1;
		try {
			Process p = runtime.exec(comando);
			retorno = p.waitFor();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(retorno != 0)
			System.out.println("Erro a execu��o do comando acima!");
		
		return retorno;
	}
}
