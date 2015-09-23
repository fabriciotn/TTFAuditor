package com.model;

public enum Status {
    ABERTO("Aberto"),
    FECHADO("Fechado"),
    ANALISE("Em análise"),
    EXECUCAO("Em execução"),
    PENDENTE_USUARIO("Pendente usuário"),
    CANCELADO("Cancelado");
    
    private String label;
    
    private Status(String label) {
		this.label = label;
	}
    
    public String getLabel(){
    	return label;
    }
    
    
}
