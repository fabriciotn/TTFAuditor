package com.testes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.facade.EstabelecimentoFacade;
import com.facade.UnidadeFacade;
import com.facade.UserFacade;
import com.model.Auditoria;
import com.model.Estabelecimento;
import com.model.Parametros;
import com.model.Pergunta;
import com.model.Role;
import com.model.Unidade;
import com.model.User;

public class CadastraUsuarios {
	private static Unidade unidade;
	private static User user;
	private static EntityManagerFactory factory;
	private static EntityManager manager;
	
	private static String banco = "auditoria_db1";
	
	public CadastraUsuarios() {
		
	}

	public static void main(String[] args) {
		criaUsuarios();
		criaParametro();
		//criaUnidades();
		//criaAuditores();
		//criaEstabelecimento();
		//criaPerguntas();
		//criaAuditoria();

		// Pergunta p = new PerguntaFacade().findPergunta(22);
		// System.out.println(p.getPergunta());
	}

	private static void criaParametro() {
		factory = Persistence.createEntityManagerFactory(banco);
		manager = factory.createEntityManager();
		manager.getTransaction().begin();

		Parametros parametro = new Parametros();
		parametro.setQuantidadeDeDiasParaAlterarAResposta(0);
		manager.persist(parametro);

		manager.getTransaction().commit();

		manager.close();
		factory.close();
		
		System.out.println("parametro criado");
	}

	private static void criaPerguntas() {
		factory = Persistence.createEntityManagerFactory(banco);
		manager = factory.createEntityManager();
		manager.getTransaction().begin();

		user = new UserFacade().findUsuario(1);
		
		Pergunta pergunta = new Pergunta();
		pergunta.setPergunta("PERGUNTA 1");
		pergunta.setHint("EXPLICAÇAO PERGUNTA1");
		pergunta.setUser(user);

		Pergunta pergunta1 = new Pergunta();
		pergunta1.setPergunta("PERGUNTA 2");
		pergunta1.setHint("EXPLICAÇAO PERGUNTA2");
		pergunta1.setUser(user);
		
		manager.persist(pergunta);
		manager.persist(pergunta1);

		manager.getTransaction().commit();

		manager.close();
		factory.close();
		
		System.out.println("perguntas criadas");
	}

	private static void criaUnidades() {
		factory = Persistence.createEntityManagerFactory(banco);
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		
		user = new UserFacade().findUsuario(1);

		unidade = new Unidade();
		unidade.setSigla("HBH");
		unidade.setNome("HEMOCENTRO DE BH");
		unidade.setEmail("hbh@hemominas.mg.gov.br");
		unidade.setEndereco("ENDERECO UNIDADE HBH");
		unidade.setTelefone("3133333333");
		unidade.setUser(user);
		
		manager.persist(unidade);
	
		manager.getTransaction().commit();

		manager.close();
		factory.close();
		
		System.out.println("Unidade criada");
	}

	private static void criaEstabelecimento() {
		factory = Persistence.createEntityManagerFactory(banco);
		manager = factory.createEntityManager();
		manager.getTransaction().begin();
		
		user = new UserFacade().findUsuario(1);
		unidade = new UnidadeFacade().findUnidade(1);

		Estabelecimento e = new Estabelecimento();
		e.setCnpj("01123654000101");
		e.setRazaoSocial("ESTABELECIMENTO DE TESTE 01 LTDA");
		e.setNomeFantasia("ESTABELECIMENTO DE TESTE 01");
		e.setEndereco("RUA DO ESTABELECIMENTO 01, 321");
		e.setCep("31000000");
		e.setCidade("BELO HORIZONTE");
		e.setUf("MG");
		e.setTelefone1("3133333333");
		e.setCnes("123123");
		e.setNomeResponsavelAT("RESPONSAVEL AT");
		e.setCargoResponsavelAT("CARGO RESPONSAVEL AT");
		e.setNomeRtAgenciaTransfusional("MEDICO RT AGENCIA");
		e.setCrmRtAgenciaTransfusional("CRM123");
		e.setUnidade(unidade);
		e.setUser(user);
		
		manager.persist(e);

		manager.getTransaction().commit();

		manager.close();
		factory.close();
		
		System.out.println("estabelecimento criado");
	}

	private static void criaAuditores() {
//		factory = Persistence.createEntityManagerFactory(banco);
//		manager = factory.createEntityManager();
//		manager.getTransaction().begin();
//
//		user = new UserFacade().findUsuario(1);
//		
//		Auditor auditor = new Auditor();
//		auditor.setNome("AUDITOR1");
//		auditor.setCelular("3199999999");
//		auditor.setTelefone("3188888888");
//		auditor.setCargo("CARGO");
//		auditor.setEmail("auditor1@hemominas.mg.gov.br");
//		auditor.setMasp("123456");
//		auditor.setUser(user);
//
//		Auditor auditor1 = new Auditor();
//		auditor1.setNome("AUDITOR2");
//		auditor1.setCelular("3100000000");
//		auditor1.setTelefone("3111111111");
//		auditor1.setCargo("CARGO2");
//		auditor1.setEmail("auditor2@hemominas.mg.gov.br");
//		auditor1.setMasp("654321");
//		auditor1.setUser(user);
//		
//		manager.persist(auditor);
//		manager.persist(auditor1);
//
//		manager.getTransaction().commit();
//
//		manager.close();
//		factory.close();
//		
//		System.out.println("auditores criados");

	}

	private static void criaUsuarios() {
		factory = Persistence.createEntityManagerFactory(banco);
		manager = factory.createEntityManager();
		manager.getTransaction().begin();

		User user = new User();
		user.setName("master");
		user.setLogin("master");
		user.setPassword("master");
		user.setEmail("master@master.com");
		user.setMenuAuditar(true);
		user.setMenuAuditoria(true);
		user.setMenuAuditoriaOff(true);
		user.setMenuAuditoriaOffExportar(true);
		user.setMenuAuditoriaOffImportar(true);
		user.setMenuCadastros(true);
		user.setMenuCadAuditorias(true);
		user.setMenuCadEstabelecimentos(true);
		user.setMenuCadPerguntas(true);
		user.setMenuCadQuestionarios(true);
		user.setMenuCadUnidades(true);
		user.setMenuConfigGerais(true);
		user.setMenuConfiguracoes(true);
		user.setMenuGerenciarUsuarios(true);
		user.setMenuMudarMinhaSenha(true);
		user.setMenuPreparacao(true);
		user.setMenuRelatorios(true);
		user.setMenuUsuarios(true);		
		user.setRole(Role.ADMIN);
		user.setAtivo(true);
		manager.persist(user);

		manager.getTransaction().commit();

		manager.close();
		factory.close();
		
		System.out.println("usuario criado");
	}

	private static void criaAuditoria() {
		factory = Persistence.createEntityManagerFactory(banco);
		manager = factory.createEntityManager();
		manager.getTransaction().begin();

		List<User> auditores = new ArrayList<User>();
		auditores.add(new UserFacade().findUsuario(1));
		auditores.add(new UserFacade().findUsuario(2));

		Estabelecimento estabelecimento = new EstabelecimentoFacade().findEstabelecimento(1);
		user = new UserFacade().findUsuario(1);

		Auditoria auditoria = new Auditoria();
		auditoria.setEstabelecimento(estabelecimento);
		auditoria.setDataDaVerificacao(new Date());
		auditoria.setUser(user);
		auditoria.setAuditores(auditores);
		auditoria.setCodigo("auditoria1");
		auditoria.setTipo("Visita");

		manager.persist(auditoria);

		manager.getTransaction().commit();

		manager.close();
		factory.close();
		
		System.out.println("auditoria criada");
	}
}