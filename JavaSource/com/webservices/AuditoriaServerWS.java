package com.webservices;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.facade.AuditoriaFacade;
import com.facade.ParametrosFacade;
import com.facade.UserFacade;
import com.model.Auditoria;
import com.model.AuditoriaHeader;
import com.model.Parametros;
import com.model.User;

@WebService
public class AuditoriaServerWS {

	UserFacade		userFacade		= new UserFacade();
	AuditoriaFacade	auditoriaFacade	= new AuditoriaFacade();

	private boolean autentica(String login, String pass) {
		User usuario = userFacade.isValidLogin(login, pass, true);
		if (usuario == null) {
			throw new RuntimeException("Usuário ou senha inválidos!");
		}
		return true;
	}

	@WebMethod
	@WebResult(name = "usuario")
	public List<User> listaUsuarios(
			@WebParam(name = "Login", header=true) String login, 
			@WebParam(name = "Senha", header=true) String pass, 
			@WebParam(name = "HostName", header=true) String hostname) {
		autentica(login, pass);
		return userFacade.listAll();
	}

	@WebMethod
	@WebResult(name = "parametro")
	public Parametros listaParametros(
			@WebParam(name = "Login", header=true) String login, 
			@WebParam(name = "Senha", header=true) String pass, 
			@WebParam(name = "HostName", header=true) String hostname) {
		autentica(login, pass);
		return new ParametrosFacade().findParametros(1);
	}

	@WebMethod
	@WebResult(name = "auditoria")
	public List<AuditoriaHeader> listaAuditoriasOff(
			@WebParam(name = "Login", header=true) String login, 
			@WebParam(name = "Senha", header=true) String pass, 
			@WebParam(name = "HostName", header=true) String hostname) {
		autentica(login, pass);
		List<AuditoriaHeader> auditoriasHeader = new ArrayList<AuditoriaHeader>();
		List<Auditoria> auditorias = auditoriaFacade.findAuditoriasOff();

		for (Auditoria auditoria : auditorias) {
			AuditoriaHeader auditoriaHeader = new AuditoriaHeader();

			auditoriaHeader.setId(auditoria.getId());
			auditoriaHeader.setCodigo(auditoria.getCodigo());
			auditoriaHeader.setNomeFantasia(auditoria.getEstabelecimento().getNomeFantasia());
			auditoriaHeader.setRazaoSocial(auditoria.getEstabelecimento().getRazaoSocial());
			auditoriaHeader.setDataDaVerificacao(auditoria.getDataDaVerificacao());

			auditoriasHeader.add(auditoriaHeader);
		}

		return auditoriasHeader;
	}

	@WebMethod
	@WebResult(name = "auditoria")
	public Auditoria findAuditoria(
			@WebParam(name = "Login", header=true) String login, 
			@WebParam(name = "Senha", header=true) String pass, 
			@WebParam(name = "HostName", header=true) String hostname, 
			@WebParam(name = "IdAuditoria") int id) {
		autentica(login, pass);
		Auditoria auditoria = auditoriaFacade.findAuditoria(id);
		return auditoria;
	}
}

/*
 * 1) AO ENTRAR NO SISTEMA, BAIXA OS DADOS DE USUÁRIOS E PARÂMETROS 
 * 2) WS PARA LISTAR AS AUDITORIAS QUE SÃO OFF-LINE E A DATA É MAIOR QUE A DATA DO DIA 
 * 3) AUDITOR SELECIONA A AUDITORIA E É NECESSÁRIO ENVIAR O ID DA AUDITORIA E BAIXAR 
 *    TODOS OS DADOS (AUDITORIA, ESTABELECIMENTO, PERGUNTAS) 
 * 4) AUDITOR REALIZA A AUDITORIA LOCAL 5) AUDITOR ENVIA AS RESPOSTAS (WS PARA ENVIO DAS RESPOSTAS 
 *    E DAS ATUALIZAÇÕES REALIZADAS NO CADASTRO DO ESTABELECIMENTO)
 */
