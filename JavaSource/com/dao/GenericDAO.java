package com.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Classe abstrata para acesso ao banco de dados.
 * Todas as classes DAO herdam desta classe genérica.
 * @author TTF Informática
 *
 * @param <T>
 */
abstract class GenericDAO<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("auditoria_db");
	private EntityManager em;

	private ResourceBundle bundle = ResourceBundle.getBundle("messages");

	private Class<T> entityClass;

	/**
	 * Abre conexão com o Banco de Dados.
	 */
	public void beginTransaction() {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
		} catch (Exception e) {
			throw new RuntimeException(bundle.getString("falhaAoConectarAoBancoDeDados"));
		}
	}
	
	protected EntityManager getEm() {
		return em;
	}

	/**
	 * Realiza o commit
	 */
	public void commit() {
		em.getTransaction().commit();
	}

	/**
	 * Realiza o rollback
	 */
	public void rollback() {
		em.getTransaction().rollback();
	}

	/**
	 * Fecha a conexão
	 */
	public void closeTransaction() {
		em.close();
	}

	/**
	 * Realiza o commit e fecha a transação
	 */
	public void commitAndCloseTransaction() {
		commit();
		closeTransaction();
	}

	public void flush() {
		em.flush();
	}

	public void joinTransaction() {
		em = emf.createEntityManager();
		em.joinTransaction();
	}

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Salva a entidade recebida via parâmetro
	 * @param entity
	 */
	public void save(T entity) {
		em.persist(entity);
	}

	/**
	 * Exclui a entidade recebida via parâmetro, de acordo com o ID.
	 * @param id
	 * @param classe
	 */
	public void delete(Object id, Class<T> classe) {
		T entityToBeRemoved = em.getReference(classe, id);
		 
        em.remove(entityToBeRemoved);
	}

	/**
	 * Realiza o update da entidade recebida via parâmetro.
	 * @param entity
	 * @return 
	 */
	public T update(T entity) {
		return em.merge(entity);
	}

	/**
	 * Realiza a busca de um registro de acordo com o ID.
	 * @param entityID
	 * @return registroBuscado
	 */
	public T find(int entityID) {
		return em.find(entityClass, entityID);
	}

	/**
	 * Realiza a busca de um registro de acordo com o ID.
	 * @param entityID
	 * @return registroBuscado
	 */
	public T findReferenceOnly(int entityID) {
		return em.getReference(entityClass, entityID);
	}

	/**
	 * Lista todos os registros da entidade
	 * Ordenando de forma ascendente, de acordo com o campo passado via parâmetro
	 * @param campo
	 * @return List<T> ListaDeRegistros
	 */
	public List<T> findAllAsc(String campo) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);
		CriteriaQuery<T> select = criteriaQuery.select(from);
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get(campo)));
		return em.createQuery(select).getResultList();
	}
	
	/**
	 * Lista todos os registros da entidade
	 * Ordenando de forma ascendente, de acordo com o ID
	 * @return List<T> ListaDeRegistros
	 */
	public List<T> findAllAsc() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);
		CriteriaQuery<T> select = criteriaQuery.select(from);
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get("id")));
		return em.createQuery(select).getResultList();
	}
	
	/**
	 * Lista todos os registros da entidade
	 * Ordenando de forma descendente, de acordo com o ID
	 * @return List<T> ListaDeRegistros
	 */
	public List<T> findAllDesc() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root<T> from = criteriaQuery.from(entityClass);
		CriteriaQuery<T> select = criteriaQuery.select(from);
		criteriaQuery.orderBy(criteriaBuilder.desc(from.get("id")));
		return em.createQuery(select).getResultList();
	}
	
	/**
	 * Using the unchecked because JPA does not have a query.getSingleResult()<T> method
	 * @param namedQuery
	 * @param parameters
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected T findOneResult(String namedQuery, Map<String, Object> parameters) {
		T result = null;

		try {
			Query query = em.createNamedQuery(namedQuery);

			// Method that will populate parameters if they are passed not null and empty
			if (parameters != null && !parameters.isEmpty()) {
				populateQueryParameters(query, parameters);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + namedQuery);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Método utilizado para realizar uma consulta SQL de acordo com a query recebia via parâmetro
	 * @param sql
	 * @return query
	 */
	public Query selectComQuery(String sql){
		Query query = em.createQuery(sql);
		return query;
	}
}
