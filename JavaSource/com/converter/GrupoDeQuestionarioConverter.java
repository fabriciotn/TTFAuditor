package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.model.GrupoDeQuestionario;

@FacesConverter(forClass = com.model.GrupoDeQuestionario.class, value="grupoDeQuestionarioConverter")
public class GrupoDeQuestionarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return GrupoDeQuestionario.class;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		GrupoDeQuestionario GrupoDeQuestionario = (GrupoDeQuestionario) arg2;
		return GrupoDeQuestionario.getLabel();
	}
}
