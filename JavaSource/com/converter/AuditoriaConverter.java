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
 * Classe de conversão de auditorias
 * @author TTF Informática
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
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
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
