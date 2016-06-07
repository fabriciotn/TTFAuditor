package com.testes;

import java.util.List;

import com.facade.RespostaFacade;
import com.model.Resposta;
import com.resource.RespostaResource;

public class ArquivosTeste {

	public static void main(String[] args) {
		// UserFacade facade = new UserFacade();
		// User user = new User();
		// user = facade.isValidLogin("fabricio1", "fabricio");

		// RelatorioAuditoria relat = new RelatorioAuditoria();
		// relat.imprimeRelatorio();
//
//		RespostaResource res = new RespostaResource(
//				"//Volumes/Arquivos/fabriciotn/Documents/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/TTFAuditor/WEB-INF/IntegracaoAuditoria/AuditoriaId183/");
//		List<Resposta> respostas = res.deserializaListaDeRespostas();
//		for (Resposta resposta : respostas) {
//			new RespostaFacade().updateResposta(resposta);
//		}	
		
		TestaCriacaoBD a =  new TestaCriacaoBD();
		String dumpExePath = "/usr/local/mysql/bin/mysqldump ";
		String host = "localhost";
		String port = "3306";
		String user = "root";
		String password = "root";
		String database = "auditoria_db";
		String backupPath = "/Volumes/Arquivos/fabriciotn/dumps/teste/";
		a.backupDataWithDatabase(dumpExePath, host, port, user, password, database, backupPath);
	}
}