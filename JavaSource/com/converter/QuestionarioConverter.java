package com.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.facade.QuestionarioFacade;
import com.model.Questionario;

/**
 * Classe de conversão dos questionários.
 * @author TTF Informática
 *
 */
@FacesConverter(forClass = com.model.Questionario.class, value="questionarioConverter")
public class QuestionarioConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		QuestionarioFacade questionarioFacade = new QuestionarioFacade();
		int questionarioId;

		try {
			questionarioId = Integer.parseInt(arg2);
		} catch (NumberFormatException exception) {
			throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na conversão", "Erro na conversão!"));
		}

		return questionarioFacade.findQuestionario(questionarioId);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

		if (arg2 == null) {
			return "";
		}
		Questionario questionario = (Questionario) arg2;
		return String.valueOf(questionario.getId());
	}
	
}
