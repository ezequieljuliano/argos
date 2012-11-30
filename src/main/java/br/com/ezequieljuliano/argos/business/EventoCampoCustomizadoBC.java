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
package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.EventoCampoCustomizado;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.com.ezequieljuliano.argos.persistence.EventoCampoCustomizadoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoCampoCustomizadoBC extends DelegateCrud<EventoCampoCustomizado, String, EventoCampoCustomizadoDAO> {

    private static final long serialVersionUID = 1L;
    @Inject
    private EventoCampoCustomizadoDAO dao;

    public void saveOrUpdate(EventoCampoCustomizado eventoCampoCustomizado) throws ValidationException {
        if (eventoCampoCustomizado.getId() == null) {
            insert(eventoCampoCustomizado);
        } else {
            update(eventoCampoCustomizado);
        }
    }

    public List<EventoCampoCustomizado> findByDescricao(String descricao) {
        return dao.findByDescricao(descricao);
    }

    public EventoCampoCustomizado findByDescricaoAndEntidade(String descricao, Entidade entidade) {
        return dao.findByDescricaoAndEntidade(descricao, entidade);
    }
}
