package com.integracao;

import java.util.List;

import com.facade.AuditoriaFacade;
import com.facade.EstabelecimentoFacade;
import com.facade.ParametrosFacade;
import com.facade.QuestionarioFacade;
import com.facade.RespostaFacade;
import com.facade.UnidadeFacade;
import com.facade.UserFacade;
import com.model.Auditoria;
import com.model.Estabelecimento;
import com.model.Questionario;
import com.model.Resposta;
import com.model.Unidade;
import com.model.User;
import com.resource.AuditoriaResource;
import com.resource.EstabelecimentoResource;
import com.resource.ParametroResource;
import com.resource.QuestionarioResource;
import com.resource.RespostaResource;
import com.resource.UnidadeResource;
import com.resource.UsuarioResource;

public class ImportaXml {

	private String caminho;

	public ImportaXml(String caminho) {
		this.caminho = caminho;
	}
	
	public void importaDoLocalParaServidor(){
		new AuditoriaFacade().updateAuditoria(new AuditoriaResource(caminho).deserializaAuditoria());
		new EstabelecimentoFacade().updateEstabelecimento(new EstabelecimentoResource(caminho).deserializaEstabelecimento());
		new ParametrosFacade().updateParametros(new ParametroResource(caminho).deserializaParametro());
		new QuestionarioFacade().updateQuestionario(new QuestionarioResource(caminho).deserializaQuestionario());
		new RespostaFacade().updateResposta(new RespostaResource(caminho).deserializaResposta());
	}
	
	public void importaDoServidorParaLocal(){
		limpaBanco();
		/**
		 * Ao realizar a instalação é necessário que os parametros sejam configurados,
		 * por isso neste caso, usamos o update.
		 */
		new ParametrosFacade().updateParametros(new ParametroResource(caminho).deserializaParametro()); 
	
		List<Unidade> unidades = new UnidadeResource(caminho).deserializaListaDeUnidades();
		for (Unidade unidade : unidades) {
			//unidade.setId(0);
			new UnidadeFacade().updateUnidade(unidade);
		}
		
		
		List<Questionario> questionarios = new QuestionarioResource(caminho).deserializaListaDeQuestionarios();
		for (Questionario questionario : questionarios) {
			//questionario.setId(0);
			new QuestionarioFacade().updateQuestionario(questionario);
		}
		
		List<User> users = new UsuarioResource(caminho).deserializaListaDeUsuarios();
		for (User user : users) {
			//user.setId(0);
			new UserFacade().updateUsuario(user);
		}
		
		Estabelecimento estabelecimento = new EstabelecimentoResource(caminho).deserializaEstabelecimento();
		estabelecimento.setId(0);
		new EstabelecimentoFacade().updateEstabelecimento(estabelecimento);
		
		Auditoria auditoria = new AuditoriaResource(caminho).deserializaAuditoria();
		//auditoria.setId(0);
		new AuditoriaFacade().updateAuditoria(auditoria);
		
		List<Resposta> respostas = new RespostaResource(caminho).deserializaListaDeRespostas();
		for (Resposta resposta : respostas) {
			//resposta.setId(0);
			new RespostaFacade().updateResposta(resposta);
		}
	}

	private void limpaBanco() {
		System.out.println("deve limpar o banco nessa hora?");
	}
}
