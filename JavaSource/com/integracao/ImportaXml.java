package com.integracao;

import com.facade.AuditoriaFacade;
import com.facade.EstabelecimentoFacade;
import com.facade.ParametrosFacade;
import com.facade.QuestionarioFacade;
import com.facade.RespostaFacade;
import com.resource.AuditoriaResource;
import com.resource.EstabelecimentoResource;
import com.resource.ParametroResource;
import com.resource.QuestionarioResource;
import com.resource.RespostaResource;

public class ImportaXml {

	private String caminho;

	public ImportaXml(String caminho) {
		this.caminho = caminho;
	}
	
	public void importa(){
		new AuditoriaFacade().updateAuditoria(new AuditoriaResource(caminho).deserializaAuditoria());
		new EstabelecimentoFacade().updateEstabelecimento(new EstabelecimentoResource(caminho).deserializaEstabelecimento());
		new ParametrosFacade().updateParametros(new ParametroResource(caminho).deserializaParametro());
		new QuestionarioFacade().updateQuestionario(new QuestionarioResource(caminho).deserializaQuestionario());
		new RespostaFacade().updateResposta(new RespostaResource(caminho).deserializaResposta());
	}
	
	
}
