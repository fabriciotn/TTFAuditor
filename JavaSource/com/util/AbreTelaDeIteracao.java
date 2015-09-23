package com.util;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class AbreTelaDeIteracao {

	public static String ID_PENDENCIA_PESQUISADA;

	
	public void abreTela(){
		ID_PENDENCIA_PESQUISADA = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"); 
		//return "cadastrarIteracao.xhtml";
	}
}
