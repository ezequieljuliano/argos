/*
 * Copyright 2012 Ezequiel Juliano Müller.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.ezequieljuliano.argos.view.converter;

import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.gov.frameworkdemoiselle.util.Beans;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@FacesConverter(value = "eventoTipoConverter")
public class EventoTipoConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.equals("")) {
            return null;
        }
        EventoTipoBC eventoTipoBC = Beans.getReference(EventoTipoBC.class);
        EventoTipo eventoTipo = eventoTipoBC.load(value);
        if (eventoTipo == null) {
            try {
                throw new Exception("Valor desconhecido para converter em Evento Tipo!");
            } catch (Exception ex) {
                Logger.getLogger(EventoTipoConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return eventoTipo;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "0";
        }
        return ((EventoTipo) value).getId();
    }
}
