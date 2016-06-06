package com.integracao;

import com.model.Auditoria;
import com.resource.AuditoriaResource;
import com.resource.EstabelecimentoResource;
import com.resource.ParametroResource;
import com.resource.QuestionarioResource;
import com.resource.RespostaResource;
import com.resource.UnidadeResource;
import com.resource.UsuarioResource;

public class ExportaXml {
	
	private String caminho;

	public ExportaXml(String caminho) {
		this.caminho = caminho;
	}

	public void exporta(Auditoria auditoria){
		ParametroResource parametrosResource = new ParametroResource(caminho);
		parametrosResource.serializaParametro();
		
		UnidadeResource unidadeResource = new UnidadeResource(caminho);
		unidadeResource.serializaListaDeUnidades();
		
		UsuarioResource usuarioResource = new UsuarioResource(caminho);
		usuarioResource.serializaListaDeUsuarios();
		
		QuestionarioResource questionarioResource = new QuestionarioResource(caminho);
		questionarioResource.serializaListaDeQuestionarios();
		
		RespostaResource respostaResource = new RespostaResource(caminho);
		respostaResource.serializaListaDeRespostas(auditoria.getId());
		
		EstabelecimentoResource estabelecimentoResource = new EstabelecimentoResource(caminho);
		estabelecimentoResource.serializaEstabelecimento(auditoria.getEstabelecimento());
		
		AuditoriaResource auditoriaResource = new AuditoriaResource(caminho);
		auditoriaResource.serializaAuditoria(auditoria);
	}
}
