package com.integracao;

import java.io.File;
import java.util.List;

import com.facade.EstabelecimentoFacade;
import com.facade.RespostaFacade;
import com.model.Resposta;
import com.resource.EstabelecimentoResource;
import com.resource.RespostaResource;

/**
 * Classe responsável por importar os XML's
 * @author TTF Informática
 *
 */
public class ImportaXml {
	
	/**
	 * Importa o XML de estabelecimento
	 * @param caminho
	 */
	public void importaEstabelecimentoParaServidor(String caminho){
		new EstabelecimentoFacade().updateEstabelecimento(new EstabelecimentoResource().deserializaEstabelecimento(caminho));
		moveArquivo(new File(caminho));
	}
	
	/**
	 * Importa o XML de respostas
	 * @param caminho
	 */
	public void importaRespostaParaServidor(String caminho){
		List<Resposta> respostas = new RespostaResource().deserializaListaDeRespostas(caminho);
		for (Resposta resposta : respostas) {
			new RespostaFacade().updateResposta(resposta);
		}
		
		moveArquivo(new File(caminho));
	}
	
	/**
	 * Move os arquivos após realizar a leitura
	 * @param file
	 */
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