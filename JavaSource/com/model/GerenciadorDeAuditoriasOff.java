package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size = 500)
@Table(name="gerenciador_auditorias")
public class GerenciadorDeAuditoriasOff implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name="auditoria_id", unique=true)
	private Auditoria auditoria;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDownload;
	@OneToOne
	private User usuarioDownload;
	private String ipDownload;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUpload;
	@OneToOne
	private User usuarioUpload;
	private String ipUpload;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Auditoria getAuditoria() {
		return auditoria;
	}
	public void setAuditoria(Auditoria auditoria) {
		this.auditoria = auditoria;
	}
	public Date getDataDownload() {
		return dataDownload;
	}
	public void setDataDownload(Date dataDownload) {
		this.dataDownload = dataDownload;
	}
	public User getUsuarioDownload() {
		return usuarioDownload;
	}
	public void setUsuarioDownload(User usuarioDownload) {
		this.usuarioDownload = usuarioDownload;
	}
	public String getHostnameDownload() {
		return ipDownload;
	}
	public void setHostnameDownload(String hostnameDownload) {
		this.ipDownload = hostnameDownload;
	}
	public Date getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(Date dataUpload) {
		this.dataUpload = dataUpload;
	}
	public User getUsuarioUpload() {
		return usuarioUpload;
	}
	public void setUsuarioUpload(User usuarioUpload) {
		this.usuarioUpload = usuarioUpload;
	}
	public String getHostnameUpload() {
		return ipUpload;
	}
	public void setHostnameUpload(String hostnameUpload) {
		this.ipUpload = hostnameUpload;
	}
	
	
}
