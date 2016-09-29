package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.AuditoriaFacade;
import com.model.Auditoria;

/**
 * Classe de convers�o de auditorias
 * @author TTF Inform�tica
 *
 */
@FacesConverter(forClass = com.model.Auditoria.class, value="auditoriaConverter")
public class AuditoriaConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		AuditoriaFacade auditoriaFacade = new AuditoriaFacade();
		int auditoriaId;

		try {
			auditoriaId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na convers�o", "Erro na convers�o!"));
		}

		return auditoriaFacade.findAuditoria(auditoriaId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Auditoria auditoria = (Auditoria) arg2;
		return String.valueOf(auditoria.getId());
	}
}
