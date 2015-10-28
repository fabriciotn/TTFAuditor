package com.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Questionario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@OneToOne
	private User user;
	private String titulo;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataDaAvaliacao;
	@OneToOne
	private Auditor auditor;
//	@OneToMany(mappedBy = "questionario")
//	private List<Pergunta> pergunta;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Calendar getDataDaAvaliacao() {
		return dataDaAvaliacao;
	}
	public void setDataDaAvaliacao(Calendar dataDaAvaliacao) {
		this.dataDaAvaliacao = dataDaAvaliacao;
	}
	public Auditor getAuditor() {
		return auditor;
	}
	public void setAuditor(Auditor auditor) {
		this.auditor = auditor;
	}
//	public List<Pergunta> getPergunta() {
//		return pergunta;
//	}
//	public void setPergunta(List<Pergunta> pergunta) {
//		this.pergunta = pergunta;
//	}
	
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
		Questionario other = (Questionario) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
