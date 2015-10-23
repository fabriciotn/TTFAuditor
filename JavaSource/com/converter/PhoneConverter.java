package com.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

@FacesConverter("phoneConverter")
public class PhoneConverter implements Converter{

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        String phoneNumber = (String) modelValue;
        return converte(phoneNumber);
    }

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        // Conversion is not necessary for now. However, if you ever intend to use 
        // it on input components, you probably want to implement it here.
        throw new UnsupportedOperationException("Not implemented yet");
    }
	
	private String converte(String phoneNumber) {
		MaskFormatter phoneFormatter;
		JFormattedTextField txtPhone = null;
		
		try {
			phoneFormatter = new MaskFormatter("(##)####-####");
			txtPhone = new JFormattedTextField(phoneFormatter);
			txtPhone.setText(phoneNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return txtPhone.getText();
	}
}