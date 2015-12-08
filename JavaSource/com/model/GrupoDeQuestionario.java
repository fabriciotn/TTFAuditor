package com.model;

public enum GrupoDeQuestionario {
	GRUPO1("Grupo 1"),
    GRUPO2("Grupo 2"),
	GRUPO3("Grupo 3");
    
    private String label;
    
    private GrupoDeQuestionario(String label) {
		this.label = label;
	}
    
    public String getLabel(){
    	return label;
    }
}
