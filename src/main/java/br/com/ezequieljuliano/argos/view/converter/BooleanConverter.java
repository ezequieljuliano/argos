/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ezequieljuliano.argos.view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ezequiel
 */
@FacesConverter(value = "booleanConverter")
public class BooleanConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        Boolean obj = (Boolean) o;
        if (obj == null) {
            return "";
        }
        if (obj.equals(Boolean.TRUE)) {
            return "Sim";
        } else if (obj.equals(Boolean.FALSE)) {
            return "Não";
        } else {
            return obj.toString();
        }
    }
}
