package com.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.model.Responsavel;

@ManagedBean
public class ResponsavelBean {

	//private Map<String, String> responsavelList = new HashMap<String, String>();
	private List<Responsavel> responsavelList = new ArrayList<Responsavel>();
	

	private Responsavel responsavel;

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	@PostConstruct
	public void init(){
			
	}
	
	public List<Responsavel> getResponsavelList() {
		responsavelList = new ArrayList<Responsavel>(Arrays.asList(Responsavel.values()));
		return responsavelList;
	}
}
