package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.AuditorFacade;
import com.model.Auditor;

@FacesConverter(forClass = com.model.Auditor.class, value="auditorConverter")
public class AuditorConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		AuditorFacade setorFacade = new AuditorFacade();
		int setorId;

		try {
			setorId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na convers�o", "Erro na convers�o!"));
		}

		return setorFacade.findAuditor(setorId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Auditor setor = (Auditor) arg2;
		return String.valueOf(setor.getId());
	}
}