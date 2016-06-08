package com.testes;

import java.io.IOException;


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
		
		//backupDoServidor();
		
		String caminhoMySql = "/usr/local/mysql/bin/";
		//String caminhoMySql = "C:/Program Files/MySQL/MySQL Server 5.6/bin/";
		
		String localBackup = "/Volumes/Arquivos/fabriciotn/dumps/teste/";
		//String localBackup = "C:/Fabricio/";
		
		String nomeArquivo = "backup.sql";
		
		String caminhoZip = "C:/Program Files/7-Zip/7z.exe";
		
		String realizaBackup = caminhoMySql + "mysqldump -h 10.14.124.14 --port 3306 -u root --password=TTFAuditorADM -B auditoria_db -r " + localBackup + nomeArquivo;
		String compacta = caminhoZip + " a -tgzip C:/Fabricio/backup.zip C:/Fabricio/backup.sql";
		String descompacta = "C:/Program Files/7-Zip/7z.exe e -tgzip C:/Fabricio/backup.zip -oC:/Fabricio";
		String restoreBackup =  "\"C:/Program Files/MySQL/MySQL Server 5.6/bin/mysql\" -u root --password=root auditoria_db < C:/Fabricio/backup.sql";
		String createDatabase = "C:/Program Files/MySQL/MySQL Server 5.6/bin/mysql -uroot -proot -e \"CREATE DATABASE IF NOT EXISTS `auditoria_db` DEFAULT CHARACTER SET utf8\"";
		executaComando(restoreBackup);
	}

	private static void executaComando(String comando) {
		
		System.out.println("Executando: " + comando);
		
		Runtime runtime = Runtime.getRuntime();
		try {
			Process p = runtime.exec(comando);
			p.waitFor();
			p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("fim");
	}
}