package com.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.model.Role;
import com.model.User;

public class CadastraUsuarios {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("auditoria_db");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		User user = new User();
		user.setName("Adriano Eustáquio Amaral");
		user.setLogin("adriano.amaral");
		user.setPassword("123");
		user.setEmail("adrianoeamaral@gmail.com");
		user.setRole(Role.ADMIN);
		manager.persist(user);

		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}
}