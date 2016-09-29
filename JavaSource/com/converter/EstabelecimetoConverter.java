package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.EstabelecimentoFacade;
import com.model.Estabelecimento;

/**
 * Classe de conversão dos estabelecimentos.
 * @author TTF Informática
 *
 */
@FacesConverter(forClass = com.model.Estabelecimento.class, value="estabelecimentoConverter")
public class EstabelecimetoConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		EstabelecimentoFacade setorFacade = new EstabelecimentoFacade();
		int setorId;

		try {
			setorId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return setorFacade.findEstabelecimento(setorId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Estabelecimento setor = (Estabelecimento) arg2;
		return String.valueOf(setor.getId());
	}
}
