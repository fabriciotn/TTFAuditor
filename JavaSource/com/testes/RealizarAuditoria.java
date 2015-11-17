package com.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.facade.AuditoriaFacade;
import com.model.Auditoria;
import com.model.Pergunta;
import com.model.Questionario;
import com.model.Resposta;

public class RealizarAuditoria {
	
	private static EntityManagerFactory factory;
	private static EntityManager manager;

	public RealizarAuditoria() {
		factory = Persistence.createEntityManagerFactory("auditoria_db");
		manager = factory.createEntityManager();
	}

	public static void main(String[] args) {
		AuditoriaFacade fac = new AuditoriaFacade();
		Auditoria findAuditoria = fac.findAuditoria(1);
		responderAuditoria(findAuditoria);
		
	}
	
	private static void responderAuditoria(Auditoria auditoria) {
		//manager.getTransaction().begin();
		
//		Resposta resposta = new Resposta();
//		
//		
//		
//		for (Questionario q : auditoria.getQuestionarios()) {
//			for (Pergunta p : q.getPerguntas()) {
//				resposta.setResposta("C");
//				resposta.setRecomendacao("Teste de recomendação " + p.getId());
//				p.setResposta(resposta);
//			}
//		}
		
		//manager.persist(resposta);

		//manager.getTransaction().commit();
		
		//manager.close();
		//factory.close();
	}
}
