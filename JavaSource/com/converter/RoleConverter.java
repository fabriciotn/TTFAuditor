package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.model.Role;

/**
 * Classe de conversão das permissões.
 * @author TTF Informática
 *
 */
@FacesConverter(forClass = com.model.Role.class, value="roleConverter")
public class RoleConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return Role.class;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Role Role = (Role) arg2;
		return Role.getLabel();
	}
}
