
package com.model;

public enum TipoServico {
    AT("Ag�ncia Transfusional"),
    AH("Assist�ncia Hemoter�pica"),
    OUTRO("Outros");
    
    private String label;
    
    private TipoServico(String label){
    	this.label = label;
    }

	public String getLabel() {
		return label;
	}
}
