package com.model;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;

@Entity
@SessionScoped
public class Parametros implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int quantidadeDeDiasParaAlterarAResposta = 0;

	public int getQuantidadeDeDiasParaAlterarAResposta() {
		return quantidadeDeDiasParaAlterarAResposta;
	}

	public void setQuantidadeDeDiasParaAlterarAResposta(
			int quantidadeDeDiasParaAlterarAResposta) {
		this.quantidadeDeDiasParaAlterarAResposta = quantidadeDeDiasParaAlterarAResposta;
	}
}
