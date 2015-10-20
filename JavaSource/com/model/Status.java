package com.model;

public enum Status {
    ATIVO("Ativo"),
    INATIVO("Inativo");
    
    private String label;
    
    private Status(String label) {
		this.label = label;
	}
    
    public String getLabel(){
    	return label;
    }
    
    public boolean isAtivo(){
    	if(this.label.equals("Ativo")){
    		return true;
    	}
    	
    	return false;
    }
}
