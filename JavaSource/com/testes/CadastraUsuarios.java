package com.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.model.User;

public class CadastraUsuarios {
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hemoproject");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		User user = new User();
		user.setName("Flávia Loureiro");
		user.setMasp("10506624");
		user.setEmail("flavia.loureiro@hemominas.mg.gov.br");
		manager.persist(user);
		
		User user1 = new User();
		user1.setName("Neide Horta");
		user1.setMasp("10495000");
		user1.setEmail("neide.horta@hemominas.mg.gov.br");
		manager.persist(user1);

		User user2 = new User();
		user2.setName("Mônica Braga");
		user2.setMasp("10496719");
		user2.setEmail("monica.braga@hemominas.mg.gov.br");
		manager.persist(user2);
		
		User user3 = new User();
		user3.setName("Heloisa Gontijo");
		user3.setMasp("10495075");
		user3.setEmail("heloisa.gontijo@hemominas.mg.gov.br");
		manager.persist(user3);
		
		User user4 = new User();
		user4.setName("Thais Borges");
		user4.setMasp("10495737");
		user4.setEmail("thais.borges@hemominas.mg.gov.br");
		manager.persist(user4);
		
		User user5 = new User();
		user5.setName("Márcia Paz");
		user5.setMasp("10496446");
		user5.setEmail("marcia.torchia@hemominas.mg.gov.br");
		manager.persist(user5);
		
		User user6 = new User();
		user6.setName("Patricia Savini");
		user6.setMasp("11755568");
		user6.setEmail("patricia.savini@hemominas.mg.gov.br");
		manager.persist(user6);
		
		User user7 = new User();
		user7.setName("Alessandro");
		user7.setMasp("11193307");
		user7.setEmail("alessandro.ferreira@hemominas.mg.gov.br");
		manager.persist(user7);
		
		User user8 = new User();
		user8.setName("Marcelo");
		user8.setMasp("10496917");
		user8.setEmail("marcelo.souza@hemominas.mg.gov.br");
		manager.persist(user8);
		
		User user9 = new User();
		user9.setName("Luciana");
		user9.setMasp("10501427");
		user9.setEmail("luciana.cayres@hemominas.mg.gov.br");
		manager.persist(user9);
		
		User user10 = new User();
		user10.setName("Sônia");
		user10.setMasp("10496099");
		user10.setEmail("sorologia@hemominas.mg.gov.br");
		manager.persist(user10);
		
		User user12 = new User();
		user12.setName("Milena");
		user12.setMasp("10506459");
		user12.setEmail("milena.oliveira@hemominas.mg.gov.br");
		manager.persist(user12);
		
		User user11 = new User();
		user11.setName("Darlene");
		user11.setMasp("3109928");
		user11.setEmail("darlene.carvalho@hemominas.mg.gov.br");
		manager.persist(user11);


		manager.getTransaction().commit();
		
		manager.close();
		factory.close();
	}
}