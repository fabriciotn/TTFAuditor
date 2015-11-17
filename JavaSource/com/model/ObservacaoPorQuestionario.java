package com.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Entity
//@Table(name = "observacao_por_questionario")
public class ObservacaoPorQuestionario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Lob
	private String obs;
	@OneToOne
	private Questionario questionario;
	@OneToOne
	private Auditoria auditoria;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public Questionario getQuestionario() {
		return questionario;
	}
	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
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
		result = prime * result + ((auditoria == null) ? 0 : auditoria.hashCode());
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
		ObservacaoPorQuestionario other = (ObservacaoPorQuestionario) obj;
		if (auditoria == null) {
			if (other.auditoria != null)
				return false;
		} else if (!auditoria.equals(other.auditoria))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	*/
}
