package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.ProjetoFacade;
import com.model.Projeto;

@FacesConverter(forClass = com.model.Projeto.class, value="projetoConverter")
public class ProjetoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		ProjetoFacade ProjetoFacade = new ProjetoFacade();
		int ProjetoId;

		try {
			ProjetoId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return ProjetoFacade.findProjeto(ProjetoId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Projeto Projeto = (Projeto) arg2;
		return String.valueOf(Projeto.getId());
	}
}
