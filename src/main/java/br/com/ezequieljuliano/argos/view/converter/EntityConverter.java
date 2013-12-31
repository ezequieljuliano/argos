/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
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

import br.com.ezequieljuliano.argos.business.EntityBC;
import br.com.ezequieljuliano.argos.domain.Entity;
import br.gov.frameworkdemoiselle.util.Beans;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Entity.class, value = "entityConverter")
public class EntityConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.equals("")) {
            return null;
        }
        EntityBC bc = Beans.getReference(EntityBC.class);
        Entity obj = bc.load(value);
        if (obj == null) {
            try {
                throw new Exception("Valor desconhecido para converter!");
            } catch (Exception ex) {
                java.util.logging.Logger.getLogger(EntityConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return obj;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if ((value == null) || (value.equals(""))) {
            return "Null";
        }
        return ((Entity) value).getId();
    }

}
