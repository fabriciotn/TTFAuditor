package com.testes;

import com.facade.AuditoriaFacade;
import com.model.Auditoria;
import com.resource.AuditoriaResource;

public class ArquivosTeste {

	public static void main(String[] args) {
		// UserFacade facade = new UserFacade();
		// User user = new User();
		// user = facade.isValidLogin("fabricio1", "fabricio");

		// RelatorioAuditoria relat = new RelatorioAuditoria();
		// relat.imprimeRelatorio();

		AuditoriaResource res = new AuditoriaResource("/Volumes/Arquivos/fabriciotn/Documents/IntegracaoAuditorias/auditoria_id_51/");
		Auditoria auditoria = res.deserializaAuditoria();

		AuditoriaFacade f = new AuditoriaFacade();
		f.updateAuditoria(auditoria);
	}
}