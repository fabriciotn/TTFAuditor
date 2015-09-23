package com.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.model.Projeto;
import com.model.Setor;

public class CadastraSetores {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hemoproject");
		EntityManager manager = factory.createEntityManager();

		manager.getTransaction().begin();

		Setor setor = new Setor();
		setor.setNome("GERÊNCIA DE C.Q.");
		manager.persist(setor);

		manager.getTransaction().commit();

		manager.close();
		factory.close();
	}
}
