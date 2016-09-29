package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.swing.text.MaskFormatter;

/**
 * Classe de conversão do CNPJ. Inclui as máscaras do campo.
 * @author TTF Informática
 *
 */
@FacesConverter("cnpjConverter")
public class CnpjConverter implements Converter{

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String cnpj = (String) modelValue;
        return converte(cnpj);
    }

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        // Conversion is not necessary for now. However, if you ever intend to use 
        // it on input components, you probably want to implement it here.
        throw new UnsupportedOperationException("Not implemented yet");
    }
	
	private String converte(Object cnpj) {
		MaskFormatter mask;
		String cnpjFormatado;
        try {
            mask = new MaskFormatter("##.###.###/####-##");
            mask.setValueContainsLiteralCharacters(false);
            cnpjFormatado = mask.valueToString(cnpj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return cnpjFormatado;
	}
}