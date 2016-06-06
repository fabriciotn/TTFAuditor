package com.testes;

import java.util.List;

import com.model.Resposta;
import com.resource.RespostaResource;

public class ArquivosTeste {

	public static void main(String[] args) {
		// UserFacade facade = new UserFacade();
		// User user = new User();
		// user = facade.isValidLogin("fabricio1", "fabricio");

		// RelatorioAuditoria relat = new RelatorioAuditoria();
		// relat.imprimeRelatorio();

		RespostaResource res = new RespostaResource(
				"/Volumes/Arquivos/fabriciotn/Documents/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/TTFAuditor/WEB-INF/IntegracaoAuditoria/AuditoriaId58/");
		List<Resposta> respostas = res.deserializaListaDeRespostas();

		for (Resposta resposta : respostas) {
			System.out.println("Id: " + resposta.getId());
			System.out.println(resposta.getPergunta());
			System.out.println("******\n");
		}
		
	}
}