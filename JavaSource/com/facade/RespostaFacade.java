package com.facade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;

import com.dao.RespostaDAO;
import com.model.Flag;
import com.model.Resposta;

public class RespostaFacade implements Serializable {

	private static final long		serialVersionUID	= 1L;

	private RespostaDAO				respostaDAO			= new RespostaDAO();
	private static SessionFactory	factory;

	private Resposta				resposta			= null;

	private Resposta persistedResposta;

	public void createResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		respostaDAO.save(resposta);
		respostaDAO.commitAndCloseTransaction();
	}

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

	public Resposta findResposta(int respostaId) {
		if (resposta == null) {
			System.out.println("pesquisou resposta");
			respostaDAO.beginTransaction();
			resposta = respostaDAO.find(respostaId);
			respostaDAO.closeTransaction();
		}

		return resposta;
	}

	public List<Resposta> listAll() {
		respostaDAO.beginTransaction();
		List<Resposta> result = respostaDAO.findAllAsc();
		respostaDAO.closeTransaction();
		return result;
	}

	public void deleteResposta(Resposta resposta) {
		respostaDAO.beginTransaction();
		Resposta persistedResposta = respostaDAO.findReferenceOnly(resposta.getId());
		respostaDAO.delete(persistedResposta);
		respostaDAO.commitAndCloseTransaction();
	}

	public List<Object[]> buscaComQuery(String sql) {
		respostaDAO.beginTransaction();
		Query query = respostaDAO.selectComQuery(sql);
		List<Object[]> list = (List<Object[]>) query.getResultList();
		respostaDAO.closeTransaction();
		return list;
	}

}