package com.integracao;

import java.io.File;
import java.util.List;

import com.facade.EstabelecimentoFacade;
import com.facade.RespostaFacade;
import com.model.Resposta;
import com.resource.EstabelecimentoResource;
import com.resource.RespostaResource;

public class ImportaXml {
	
	public void importaEstabelecimentoParaServidor(String caminho){
		new EstabelecimentoFacade().updateEstabelecimento(new EstabelecimentoResource().deserializaEstabelecimento(caminho));
		moveArquivo(new File(caminho));
	}
	
	public void importaRespostaParaServidor(String caminho){
		List<Resposta> respostas = new RespostaResource().deserializaListaDeRespostas(caminho);
		for (Resposta resposta : respostas) {
			new RespostaFacade().updateResposta(resposta);
		}
		
		moveArquivo(new File(caminho));
	}
	
	public void moveArquivo(File file){
		String fullPath = file.getAbsolutePath().replace(file.getName(), "") + "\\importados";
		File newDir = new File(fullPath);
		if(!(newDir.exists()) && newDir.mkdir()){
			System.out.println("Criou pasta " + fullPath );
		}
		
		String currentPath = file.getPath().replace("\\" + file.getName(), "");
		if(file.renameTo(new File(currentPath + "\\importados", file.getName()))){
			System.out.println("Importou: " + currentPath + "\\importados\\" + file.getName());
		}else{
			System.out.println("NÃO Importou: " + currentPath + "\\importados\\" + file.getName());
		}
	}
}