package com.mb;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.facade.ParametrosFacade;
import com.model.Parametros;
import com.model.User;

@RequestScoped
@ManagedBean
public class ParametrosMB extends AbstractMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private Parametros parametros;
	private ParametrosFacade parametrosFacade;

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
	}

	public void resetParametros() {
		parametros = new Parametros();
	}
}
