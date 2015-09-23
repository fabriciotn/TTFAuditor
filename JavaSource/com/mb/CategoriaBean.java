package com.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import com.model.Categoria;

@ManagedBean
public class CategoriaBean {

	private List<Categoria> categoriaList = new ArrayList<Categoria>();
	private Categoria categoria;
	
	@PostConstruct
	public void init(){
		
	}

	public List<Categoria> getCategoriaList() {
		categoriaList = new ArrayList<Categoria>(Arrays.asList(Categoria.values()));
		return categoriaList;
	}

	public void setCategoriaList(List<Categoria> categoriaList) {
		this.categoriaList = categoriaList;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
