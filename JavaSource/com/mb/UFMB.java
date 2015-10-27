package com.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.model.UF;

@ManagedBean
public class UFMB {

	private List<UF> ufList = new ArrayList<UF>();
	private UF uf;
	
	@PostConstruct
	public void init(){
		
	}

	public List<UF> getUFList() {
		ufList = new ArrayList<UF>(Arrays.asList(UF.values()));
		return ufList;
	}

	public void setUFList(List<UF> ufList) {
		this.ufList = ufList;
	}

	public UF getUF() {
		return uf;
	}

	public void setUF(UF uf) {
		this.uf = uf;
	}
	
	
}