package com.model;

import java.util.Date;

/**
 * Classe modelo para o Header de auditorias
 * Não é utilizado hoje. Será utilizado somente quando o sistema passar a trabalhar com o WebServices
 * @author TTF Informática
 *
 */
public class AuditoriaHeader {

	private int id;
	private String codigo;
	private String razaoSocial;
	private String nomeFantasia;
	private Date dataDaVerificacao;
	
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
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public Date getDataDaVerificacao() {
		return dataDaVerificacao;
	}
	public void setDataDaVerificacao(Date dataDaVerificacao) {
		this.dataDaVerificacao = dataDaVerificacao;
	}
	
	
}
