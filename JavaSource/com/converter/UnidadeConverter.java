package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.UnidadeFacade;
import com.model.Unidade;

@FacesConverter(forClass = com.model.Unidade.class, value="unidadeConverter")
public class UnidadeConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		UnidadeFacade unidadeFacade = new UnidadeFacade();
		int unidadeId;

		try {
			unidadeId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return unidadeFacade.findUnidade(unidadeId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Unidade unidade = (Unidade) arg2;
		return String.valueOf(unidade.getId());
	}
}
