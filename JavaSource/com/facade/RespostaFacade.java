package com.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.RespostaDAO;
import com.model.Flag;
import com.model.Resposta;

/**
 * Classe fachada para acesso ao banco de dados.
 * @author TTF Informática
 *
 */
public class RespostaFacade implements Serializable {

	private static final long		serialVersionUID	= 1L;

	private RespostaDAO				respostaDAO			= new RespostaDAO();
	private static SessionFactory	factory;

	private Resposta				resposta			= null;

	private Resposta persistedResposta;

	/**
	 * Cria uma nova resposta
	 * @param resposta
	 */
	public void createResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		respostaDAO.save(resposta);
		respostaDAO.commitAndCloseTransaction();
	}

	/**
	 * Atualiza uma resposta
	 * @param resposta
	 */
	public void updateResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		
		if(persistedResposta == null)
			persistedResposta = respostaDAO.find(resposta.getId());
		
		if (resposta.getResposta() != null)
			persistedResposta.setResposta(resposta.getResposta());
		if (resposta.getUser() != null)
			persistedResposta.setUser(resposta.getUser());
		if (resposta.getQuestionario() != null)
			persistedResposta.setQuestionario(resposta.getQuestionario());

		if (resposta.getRecomendacao() != null) {
			if (resposta.getResposta() != null && resposta.getResposta().equals("NC") && persistedResposta.getRecomendacaoPadrao() != null
					&& !persistedResposta.getRecomendacaoPadrao().equals("")) {
				String novaRecomendacao = persistedResposta.getRecomendacaoPadrao() + "\n" + resposta.getRecomendacao();

				resposta.setRecomendacao(novaRecomendacao);
			} else {
				if (persistedResposta.getTipoDeResposta() == Flag.B_PERGUNTA)
					resposta.setRecomendacao(resposta.getRecomendacao());
				else
					persistedResposta.setResposta("-");
			}
			persistedResposta.setRecomendacao(resposta.getRecomendacao());
		}

		persistedResposta.setDataDaResposta(new Date());

		respostaDAO.update(persistedResposta);
		respostaDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de uma resposta de acordo com o ID
	 * @param respostaId
	 * @return resposta
	 */
	public Resposta findResposta(int respostaId) {
		if (resposta == null) {
			respostaDAO.beginTransaction();
			resposta = respostaDAO.find(respostaId);
			respostaDAO.closeTransaction();
		}

		return resposta;
	}

	/**
	 * Busca todas as respostas
	 * @return listaDeRespostas
	 */
	public List<Resposta> listAll() {
		respostaDAO.beginTransaction();
		List<Resposta> result = respostaDAO.findAllAsc();
		respostaDAO.closeTransaction();
		return result;
	}
	
	/**
	 * Lista todas as repostas de uma auditoria pelo ID da auditoria
	 * @param auditoria_id
	 * @return listaDeRespostasPorIdDaAuditoria
	 */
	public List<Resposta> listaRespostasPorIdDaAuditoria(int auditoria_id) {
		respostaDAO.beginTransaction();
		List<Resposta> result = respostaDAO.listaRespostasPorIdDaAuditoria(auditoria_id);
		respostaDAO.closeTransaction();
		return result;
	}

	/**
	 * Deleta uma resposta
	 * @param resposta
	 */
	public void deleteResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		Resposta persistedResposta = respostaDAO.findReferenceOnly(resposta.getId());
		respostaDAO.delete(persistedResposta);
		respostaDAO.commitAndCloseTransaction();
	}

	/**
	 * Realiza a busca de acordo com a query recebida via parâmetro
	 * @param sql
	 * @return listaDeObjetos
	 */
	public List<Object[]> buscaComQuery(String sql) {
		respostaDAO.beginTransaction();
		Query query = respostaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>) query.getResultList();
		respostaDAO.closeTransaction();
		return list;
	}

}