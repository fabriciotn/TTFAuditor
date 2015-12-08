package com.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.model.GrupoDeQuestionario;

@ManagedBean
public class GrupoDeQuestionarioBean {

	private List<GrupoDeQuestionario> grupoDeQuestionarioList = new ArrayList<GrupoDeQuestionario>();
	
	private GrupoDeQuestionario grupoDeQuestionario;

	public GrupoDeQuestionario getGrupoDeQuestionario() {
		return grupoDeQuestionario;
	}

	public void setGrupoDeQuestionario(GrupoDeQuestionario grupoDeQuestionario) {
		this.grupoDeQuestionario = grupoDeQuestionario;
	}

	@PostConstruct
	public void init(){
			
	}
	
	public List<GrupoDeQuestionario> getGrupoDeQuestionarioList() {
		grupoDeQuestionarioList = new ArrayList<GrupoDeQuestionario>(Arrays.asList(GrupoDeQuestionario.values()));
		return grupoDeQuestionarioList;
	}
}
