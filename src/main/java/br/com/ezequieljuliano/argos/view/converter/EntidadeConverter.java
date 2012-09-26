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

import br.com.ezequieljuliano.argos.business.EntidadeBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
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
@FacesConverter(value = "entidadeConverter")
public class EntidadeConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.equals("")) {
            return null;
        }
        EntidadeBC entidadeBC = Beans.getReference(EntidadeBC.class);
        Entidade entidade = entidadeBC.load(value);
        if (entidade == null) {
            try {
                throw new Exception("Valor desconhecido para converter em Entidade!");
            } catch (Exception ex) {
                Logger.getLogger(EntidadeConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return entidade;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if ((value == null) || (value.equals(""))) {
            return "0";
        }
        return ((Entidade) value).getId();
    }
}
