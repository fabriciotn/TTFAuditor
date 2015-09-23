package com.model;

public enum Responsavel {
	SOFIS("Sofis"),
	HEMOMINAS("Hemominas");
	
	 private String label;
	    
	    private Responsavel(String label) {
			this.label = label;
		}
	    
	    public String getLabel(){
	    	return label;
	    }
}
