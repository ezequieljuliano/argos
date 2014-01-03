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
package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.domain.Marker;
import br.com.ezequieljuliano.argos.exception.BusinessException;
import br.com.ezequieljuliano.argos.template.StantardBC;
import br.com.ezequieljuliano.argos.persistence.MarkerDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.util.List;

@BusinessController
public class MarkerBC extends StantardBC<Marker, String, MarkerDAO> {

    public List<Marker> findByName(String name) {
        return getDelegate().findByName(name);
    }

    public Marker findOneByName(String name) {
        return getDelegate().findOneByName(name);
    }

    public List<Marker> findByParent(Marker parent) {
        return getDelegate().findByParent(parent);
    }

    public Marker validateAndSave(Marker obj) throws BusinessException {
        if (Strings.isEmpty(obj.getName())) {
            throw new BusinessException("O marcador deve possuir um nome!");
        }
        if (obj.getParent() != null) {
            if (obj.getId().equals(obj.getParent().getId())) {
                throw new BusinessException("Não é possível auto-referenciar um marcador!");
            }
        }
        return save(obj);
    }

    public void validateAndDelete(final String id) throws BusinessException {
        if (!findByParent(load(id)).isEmpty()) {
            throw new BusinessException("Este marcador está vinculado a outro! Impossível deletar.");
        }
        getDelegate().delete(id);
    }

    public void validateAndDeleteObj(final Marker obj) throws BusinessException {
        if (!findByParent(obj).isEmpty()) {
            throw new BusinessException("Este marcador está vinculado a outro! Impossível deletar.");
        }
        getDelegate().deleteObj(obj);
    }

}
