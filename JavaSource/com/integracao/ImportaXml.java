package com.integracao;

import java.util.List;

import com.facade.EstabelecimentoFacade;
import com.facade.RespostaFacade;
import com.model.Resposta;
import com.resource.EstabelecimentoResource;
import com.resource.RespostaResource;

public class ImportaXml {
	
	public void importaEstabelecimentoParaServidor(String caminho){
		new EstabelecimentoFacade().updateEstabelecimento(new EstabelecimentoResource().deserializaEstabelecimento(caminho));
	}
	
	public void importaRespostaParaServidor(String caminho){
		List<Resposta> respostas = new RespostaResource().deserializaListaDeRespostas(caminho);
		for (Resposta resposta : respostas) {
			new RespostaFacade().updateResposta(resposta);
		}
	}
}
