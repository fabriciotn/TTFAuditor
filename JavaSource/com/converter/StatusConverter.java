package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.model.Status;

/**
 * Classe de conversão dos Status.
 * @author TTF Informática
 *
 */
@FacesConverter(forClass = com.model.Status.class, value="statusConverter")
public class StatusConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return Status.class;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Status Status = (Status) arg2;
		return Status.getLabel();
	}
}
