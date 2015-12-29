package com.mb;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.facade.ParametrosFacade;
import com.model.Parametros;

@RequestScoped
@ManagedBean
public class ParametrosMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Parametros parametros;
	private ParametrosFacade parametrosFacade;
	
	@PostConstruct
    public void init(){
        this.parametros = getParametrosFacade().findParametros(1);
    }

	public ParametrosFacade getParametrosFacade() {
		if (parametrosFacade == null) {
			parametrosFacade = new ParametrosFacade();
		}
		return parametrosFacade;
	}

	public void setParametrosFacade(ParametrosFacade parametrosFacade) {
		this.parametrosFacade = parametrosFacade;
	}

	public Parametros getParametros() {
		if (parametros == null) {
			parametros = new Parametros();
		}

		return parametros;
	}

	public void setParametros(Parametros parametros) {
		this.parametros = parametros;
	}

	public void updateParametros() {
		try {
			getParametrosFacade().updateParametros(parametros);
			closeDialog();
			displayInfoMessageToUser("Atualizado com sucesso!");
			resetParametros();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Ocorreu algum problema. Tente novamente!");
			e.printStackTrace();
		}
		init();
	}

	public void resetParametros() {
		parametros = new Parametros();
	}
}
