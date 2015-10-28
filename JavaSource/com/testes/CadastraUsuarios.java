package com.testes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.facade.AuditorFacade;
import com.facade.EstabelecimentoFacade;
import com.facade.PerguntaFacade;
import com.facade.UserFacade;
import com.model.Auditor;
import com.model.Auditoria;
import com.model.Estabelecimento;
import com.model.Pergunta;
import com.model.Role;
import com.model.User;

public class CadastraUsuarios {
	public static void main(String[] args) {
		//criaUsuarios();
		//criaAuditoria();
		
		Pergunta p = new PerguntaFacade().findPergunta(22);
		System.out.println(p.getPergunta());
	}

	private static void criaUsuarios() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("auditoria_db");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		User user = new User();
		user.setName("master");
		user.setLogin("123");
		user.setPassword("123");
		user.setEmail("master@master.com");
		user.setRole(Role.ADMIN);
		user.setAtivo(true);
		manager.persist(user);

		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}
	
	private static void criaAuditoria(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("auditoria_db");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		
		
		
		List<Auditor> auditores = new ArrayList<Auditor>();
		auditores.add(new AuditorFacade().findAuditor(1));
		auditores.add(new AuditorFacade().findAuditor(6));
		
		Estabelecimento estabelecimento = new EstabelecimentoFacade().findEstabelecimento(1);
		User user = new UserFacade().findUsuario(1);
		
		Auditoria auditoria = new Auditoria();
		auditoria.setEstabelecimento(estabelecimento);
		auditoria.setDataDaVerificacao(new Date());
		auditoria.setUser(user);
		auditoria.setAuditores(auditores);
		
		manager.persist(auditoria);

		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}
}