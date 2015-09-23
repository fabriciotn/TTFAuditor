package com.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SequenceGenerator(name = "SEQ_PENDENCIA", sequenceName = "SEQ_PENDENCIA", initialValue=1, allocationSize=1)
public class Pendencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PENDENCIA")
	private int id;
	private String titulo;
	@OneToOne
	private Setor setor;
	@OneToOne
	private Projeto projeto;
	@OneToOne
	private User usuario;
	private String itemDoEdital;
	@Lob
	private String descricao;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Enumerated(EnumType.STRING)
	private Prioridade prioridade;
	@Enumerated(EnumType.STRING)
    private Categoria categoria;
	@Enumerated(EnumType.STRING)
	private Responsavel responsavel;
	@Lob
	private String obs;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataDaPendencia;

	@OneToMany(mappedBy = "pendencia")
	@OrderBy(value = "dataDaPendencia DESC")
	private List<Iteracao> iteracoes;

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public List<Iteracao> getIteracoes() {
		return iteracoes;
	}

	public void setIteracoes(List<Iteracao> iteracoes) {
		this.iteracoes = iteracoes;
	}
	
	

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public String getItemDoEdital() {
		return itemDoEdital;
	}

	public void setItemDoEdital(String itemDoEdital) {
		this.itemDoEdital = itemDoEdital;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public int getId() {
		return id;
	}

	public Calendar getDataDaPendencia() {
		return dataDaPendencia;
	}

	public void setDataDaPendencia(Calendar dataDaPendencia) {
		this.dataDaPendencia = dataDaPendencia;
	}

	public void setId(int id) {
		this.id = id;
	}

}
