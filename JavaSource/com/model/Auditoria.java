package com.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Classe modelo para Auditorias
 * @author TTF Informática
 *
 */
@Entity
@BatchSize(size = 500)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Auditoria implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique = true, nullable = false)
	private String codigo;
	@OneToOne
	private User user;
	@OneToOne
	private Estabelecimento estabelecimento;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDaVerificacao;
	
	@OneToMany(mappedBy = "auditoria", targetEntity = Resposta.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@OrderBy(value = "tipoDeResposta ASC, id ASC")
	@Fetch(FetchMode.SELECT)
	private List<Resposta> respostas;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "auditoria_auditor",
	joinColumns = @JoinColumn(name = "auditoria_id"), 
	inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> auditores;
	
	@Lob
	private String observacoesGerais;
	
	/*
	 * Visita ou Revisita
	 */
	private String tipo;
	private boolean offLine;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public List<User> getAuditores() {
		return auditores;
	}

	public void setAuditores(List<User> auditores) {
		this.auditores = auditores;
	}

	public Date getDataDaVerificacao() {
		return dataDaVerificacao;
	}

	public void setDataDaVerificacao(Date dataDaVerificacao) {
		this.dataDaVerificacao = dataDaVerificacao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isOffLine() {
		return offLine;
	}

	public void setOffLine(boolean offLine) {
		this.offLine = offLine;
	}

	public String getObservacoesGerais() {
		return observacoesGerais;
	}

	public void setObservacoesGerais(String observacoesGerais) {
		this.observacoesGerais = observacoesGerais;
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
		Auditoria other = (Auditoria) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public Auditoria() {
	}
	
	public Auditoria(int id) {
		this.id = id;
	}
}
