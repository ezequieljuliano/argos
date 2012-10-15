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

import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.persistence.EventoDAO;
import br.com.ezequieljuliano.argos.util.Utils;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoBC extends DelegateCrud<Evento, String, EventoDAO> {

    private static final long serialVersionUID = 1L;

    public void saveOrUpdate(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(Utils.getUniqueId());
            insert(evento);
        } else {
            update(evento);
        }
    }
}