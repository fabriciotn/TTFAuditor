package com.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Auditoria implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(unique=true, nullable=false)
	private String codigo;
	@OneToOne
	private User user;
	@OneToOne
	private Estabelecimento estabelecimento;
	@ManyToMany
	@JoinTable(name = "auditoria_auditor",
		joinColumns = @JoinColumn(name = "auditoria_id"),
		inverseJoinColumns = @JoinColumn(name = "auditor_id") )
	private List<Auditor> auditores;
	@ManyToMany
	@JoinTable(name = "auditoria_questionario",
		joinColumns = @JoinColumn(name = "auditoria_id"),
		inverseJoinColumns = @JoinColumn(name = "questionario_id") )
	private List<Questionario> questionarios;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDaVerificacao;
	private String tipo; // visita / revisita

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

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public List<Auditor> getAuditores() {
		return auditores;
	}

	public void setAuditores(List<Auditor> auditores) {
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

	public List<Questionario> getQuestionarios() {
		return questionarios;
	}

	public void setQuestionarios(List<Questionario> questionarios) {
		this.questionarios = questionarios;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
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

}
