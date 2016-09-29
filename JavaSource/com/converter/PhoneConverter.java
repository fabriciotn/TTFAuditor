package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.swing.text.MaskFormatter;

/**
 * Classe para conversão dos números de telefone. Inclui as máscaras dos campos, de acordo com a quantidade de dígitos.
 * @author TTF Informática
 *
 */
@FacesConverter("phoneConverter")
public class PhoneConverter implements Converter {

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
		String phoneNumber = (String) modelValue;
		return converte(phoneNumber);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
		// Conversion is not necessary for now. However, if you ever intend to
		// use
		// it on input components, you probably want to implement it here.
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private String converte(Object phoneNumber) {
		MaskFormatter mask;
		String stringFormatada;
		try {
			if (phoneNumber.toString().length() == 10) {
				mask = new MaskFormatter("(##)####-####");
			} else {
				mask = new MaskFormatter("(##)#####-####");
			}
			mask.setValueContainsLiteralCharacters(false);
			stringFormatada = mask.valueToString(phoneNumber);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return stringFormatada;
	}
}