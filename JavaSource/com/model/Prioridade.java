
package com.model;

public enum Prioridade {
    LEVE("Leve"),
    MODERADO("Moderado"),
    GRAVE("Grave");
    
    private String label;
    
    private Prioridade(String label){
    	this.label = label;
    }

	public String getLabel() {
		return label;
	}
}
