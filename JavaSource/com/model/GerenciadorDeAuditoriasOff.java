package com.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private Auditoria auditoria;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataDownload;
	@OneToOne
	private User usuarioDownload;
	private String hostnameDownload;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUpload;
	@OneToOne
	private User usuarioUpload;
	private String hostnameUpload;
	
}
