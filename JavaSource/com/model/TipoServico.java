
package com.model;

public enum TipoServico {
    AT("Agência Transfusional"),
    AH("Assistência Hemoterápica"),
    OUTRO("Outros");
    
    private String label;
    
    private TipoServico(String label){
    	this.label = label;
    }

	public String getLabel() {
		return label;
	}
}
