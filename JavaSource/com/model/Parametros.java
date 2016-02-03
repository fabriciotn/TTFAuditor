package com.model;

import java.io.Serializable;

import javax.faces.bean.RequestScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.BatchSize;

@Entity
@RequestScoped
@BatchSize(size = 500)
public class Parametros implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int quantidadeDeDiasParaAlterarAResposta;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantidadeDeDiasParaAlterarAResposta() {
		return quantidadeDeDiasParaAlterarAResposta;
	}

	public void setQuantidadeDeDiasParaAlterarAResposta(
			int quantidadeDeDiasParaAlterarAResposta) {
		this.quantidadeDeDiasParaAlterarAResposta = quantidadeDeDiasParaAlterarAResposta;
	}
}
