package com.integracao;

import com.model.Auditoria;
import com.resource.EstabelecimentoResource;
import com.resource.RespostaResource;

/**
 * Classe responsável pela exportação dos arquivos XML
 * @author TTF Informática
 *
 */
public class ExportaXml {
	
	private String caminho;

	public ExportaXml(String caminho) {
		this.caminho = caminho;
	}

	public void exporta(Auditoria auditoria){
		RespostaResource respostaResource = new RespostaResource();
		respostaResource.serializaListaDeRespostas(caminho, auditoria.getId());
		
		EstabelecimentoResource estabelecimentoResource = new EstabelecimentoResource();
		estabelecimentoResource.serializaEstabelecimento(caminho, auditoria.getEstabelecimento());
	}
}
