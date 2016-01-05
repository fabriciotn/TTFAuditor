package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SessionScoped
public class Resposta implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Enumerated(EnumType.STRING)
	private Flag tipoDeResposta;
	@OneToOne
	private User user;
	@OneToOne
	private Questionario questionario;
	@Lob
	private String hint;
	@Lob
	private String pergunta;
	@Lob
	private String resposta;
	@Lob
	private String recomendacao;
	@Lob
	private String recomendacaoPadrao;
	private String tipoServico;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="auditoria_id")
	private Auditoria auditoria;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDaResposta;

	public Flag getTipoDeResposta() {
		return tipoDeResposta;
	}

	public void setTipoDeResposta(Flag tipoDeResposta) {
		this.tipoDeResposta = tipoDeResposta;
	}

	public Date getDataDaResposta() {
		return dataDaResposta;
	}

	public void setDataDaResposta(Date dataDaResposta) {
		this.dataDaResposta = dataDaResposta;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public String getPergunta() {
		return pergunta;
	}

	public void setPergunta(String pergunta) {
		this.pergunta = pergunta;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public String getRecomendacao() {
		return recomendacao;
	}

	public void setRecomendacao(String recomendacao) {
		this.recomendacao = recomendacao;
	}

	public String getRecomendacaoPadrao() {
		return recomendacaoPadrao;
	}

	public void setRecomendacaoPadrao(String recomendacaoPadrao) {
		this.recomendacaoPadrao = recomendacaoPadrao;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public Auditoria getAuditoria() {
		return auditoria;
	}

	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resposta other = (Resposta) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
