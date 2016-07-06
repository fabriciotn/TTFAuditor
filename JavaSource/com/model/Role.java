package com.model;

public enum Role {
	ADMIN("Administrador"),
	GESTOR("Gestor de unidade"),
	AUDITOR("Auditor");

	private String label;
	
	private Role(String label){
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}


