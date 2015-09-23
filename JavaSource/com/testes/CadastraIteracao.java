package com.testes;

import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.dao.UserDAO;
import com.facade.PendenciaFacade;
import com.model.Categoria;
import com.model.Iteracao;
import com.model.Pendencia;
import com.model.Status;
import com.model.User;

public class CadastraIteracao {
	public static void main(String[] args) {
		/*
		Pendencia pendencia0 = new  PendenciaFacade().findPendencia(425);
		Pendencia pendencia1 = new  PendenciaFacade().findPendencia(427);
		Pendencia pendencia2 = new  PendenciaFacade().findPendencia(428);
		Pendencia pendencia3 = new  PendenciaFacade().findPendencia(429);
		Pendencia pendencia4 = new  PendenciaFacade().findPendencia(82);
		Pendencia pendencia5 = new  PendenciaFacade().findPendencia(338);
		Pendencia pendencia6 = new  PendenciaFacade().findPendencia(26);
		Pendencia pendencia7 = new  PendenciaFacade().findPendencia(27);
		Pendencia pendencia8 = new  PendenciaFacade().findPendencia(28);
		Pendencia pendencia9 = new  PendenciaFacade().findPendencia(29);
		Pendencia pendencia10 = new  PendenciaFacade().findPendencia(30);
		Pendencia pendencia11 = new  PendenciaFacade().findPendencia(31);
		Pendencia pendencia12 = new  PendenciaFacade().findPendencia(32);
		Pendencia pendencia13 = new  PendenciaFacade().findPendencia(37);
		Pendencia pendencia14 = new  PendenciaFacade().findPendencia(38);
		Pendencia pendencia15 = new  PendenciaFacade().findPendencia(278);
		Pendencia pendencia16 = new  PendenciaFacade().findPendencia(65);
		Pendencia pendencia17 = new  PendenciaFacade().findPendencia(80);
		Pendencia pendencia18 = new  PendenciaFacade().findPendencia(341);
		Pendencia pendencia19 = new  PendenciaFacade().findPendencia(213);
		Pendencia pendencia20 = new  PendenciaFacade().findPendencia(420);
		Pendencia pendencia21 = new  PendenciaFacade().findPendencia(421);
		Pendencia pendencia22 = new  PendenciaFacade().findPendencia(196);
		Pendencia pendencia23 = new  PendenciaFacade().findPendencia(348);
		
		insereIteracao(pendencia0);
		insereIteracao(pendencia1);
		insereIteracao(pendencia2);
		insereIteracao(pendencia3);
		insereIteracao(pendencia4);
		insereIteracao(pendencia5);
		insereIteracao(pendencia6);
		insereIteracao(pendencia7);
		insereIteracao(pendencia8);
		insereIteracao(pendencia9);
		insereIteracao(pendencia10);
		insereIteracao(pendencia11);
		insereIteracao(pendencia12);
		insereIteracao(pendencia13);
		insereIteracao(pendencia14);
		insereIteracao(pendencia15);
		insereIteracao(pendencia16);
		insereIteracao(pendencia17);
		insereIteracao(pendencia18);
		insereIteracao(pendencia19);
		insereIteracao(pendencia20);
		insereIteracao(pendencia21);
		insereIteracao(pendencia22);
		insereIteracao(pendencia23);
		*/

	}
		

	private static void insereIteracao(Pendencia pendencia) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hemoproject");
		EntityManager manager = factory.createEntityManager();
		
		manager.getTransaction().begin();
		
		UserDAO usuariodao = new UserDAO();
		
		//obtem o usuário
		usuariodao.beginTransaction();
		User usuario = usuariodao.findUserByMasp("@Importacao");
		usuariodao.closeTransaction();
		
		Iteracao iteracao = new Iteracao();
		//iteracao.setCategoria(Categoria.CONFIGURACAO);
		iteracao.setDescricao("Pendente de validação do usuário. Conforme e-mail enviado.");
		iteracao.setStatus(Status.PENDENTE_USUARIO);
		iteracao.setUsuario(usuario);
		iteracao.setPendencia(pendencia);
		iteracao.setDataDaPendencia(new GregorianCalendar().getInstance());
		
		manager.persist(iteracao);
		manager.getTransaction().commit();

		manager.close();
		factory.close();
		
		System.out.println("gravou");
	}
}
