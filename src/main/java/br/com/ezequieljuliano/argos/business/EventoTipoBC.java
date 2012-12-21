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

import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.domain.Situacao;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.com.ezequieljuliano.argos.persistence.EventoTipoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoTipoBC extends DelegateCrud<EventoTipo, String, EventoTipoDAO> {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private EventoTipoDAO dao;

    public void saveOrUpdate(EventoTipo eventoTipo) throws ValidationException {
        //Verifica se código já não foi cadastrado
        EventoTipo objCodigo = findByCodigo(eventoTipo.getCodigo());
        if (objCodigo != null && !objCodigo.getId().equals(eventoTipo.getId())) {
            throw new ValidationException("Código já cadastrado!");
        }
        if (eventoTipo.getId() == null) {
            dao.insert(eventoTipo);
        } else {
            dao.update(eventoTipo);
        }
    }

    public void inativar(EventoTipo eventoTipo) throws ValidationException {
        if (eventoTipo.isAtivo()) {
            eventoTipo.setSituacao(Situacao.inativo);
            saveOrUpdate(eventoTipo);
        }
    }

    public void ativar(EventoTipo eventoTipo) throws ValidationException {
        if (eventoTipo.isInativo()) {
            eventoTipo.setSituacao(Situacao.ativo);
            saveOrUpdate(eventoTipo);
        }
    }

    public EventoTipo findByCodigo(int codigo) {
        return dao.findByCodigo(codigo);
    }
}
