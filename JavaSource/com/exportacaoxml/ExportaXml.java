package com.exportacaoxml;

import com.model.Auditoria;
import com.resource.AuditoriaResource;
import com.resource.EstabelecimentoResource;
import com.resource.ParametroResource;
import com.resource.QuestionarioResource;
import com.resource.RespostaResource;

public class ExportaXml {
	
	private String caminho;

	public ExportaXml(String caminho) {
		this.caminho = caminho;
	}

	public void exporta(Auditoria auditoria){
		AuditoriaResource res = new AuditoriaResource(caminho);
		res.serializaAuditoria(auditoria);

		EstabelecimentoResource estabelecimentoResource = new EstabelecimentoResource(caminho);
		estabelecimentoResource.serializaEstabelecimento(auditoria.getEstabelecimento());
		
		ParametroResource parametrosResource = new ParametroResource(caminho);
		parametrosResource.serializaParametro();
		
		QuestionarioResource questionarioResource = new QuestionarioResource(caminho);
		questionarioResource.serializaListaDeQuestionarios();
		
		RespostaResource respostaResource = new RespostaResource(caminho);
		respostaResource.serializaListaDeRespostas(auditoria.getRespostas());
	}
}
