package com.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.model.Prioridade;

@ManagedBean
public class PrioridadeBean {

	private List<Prioridade> prioridadeList = new ArrayList<Prioridade>();
	private Prioridade prioridade;
	
	@PostConstruct
	public void init(){
		
	}

	public List<Prioridade> getPrioridadeList() {
		prioridadeList = new ArrayList<Prioridade>(Arrays.asList(Prioridade.values()));
		return prioridadeList;
	}

	public void setPrioridadeList(List<Prioridade> prioridadeList) {
		this.prioridadeList = prioridadeList;
	}

	public Prioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}
	
	
}
