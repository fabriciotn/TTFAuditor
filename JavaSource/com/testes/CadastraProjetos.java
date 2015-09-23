package com.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.model.Projeto;

public class CadastraProjetos {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hemoproject");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		Projeto projeto = new Projeto();
		projeto.setNome("CICLO DO SANGUE");
		manager.persist(projeto);
		
			
		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}
}
