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

import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.Situacao;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.com.ezequieljuliano.argos.persistence.EventoNivelDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import java.util.List;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoNivelBC extends GenericBC<EventoNivel, String, EventoNivelDAO> {

    private static final long serialVersionUID = 1L;

    public void saveOrUpdate(EventoNivel eventoNivel) throws ValidationException {
        //Verifica se código já não foi cadastrado
        EventoNivel objCodigo = findByCodigo(eventoNivel.getCodigo());
        if (objCodigo != null && !objCodigo.getId().equals(eventoNivel.getId())) {
            throw new ValidationException("Código já cadastrado!");
        }
        if (eventoNivel.getId() == null) {
            getDAO().insert(eventoNivel);
        } else {
            getDAO().save(eventoNivel);
        }
    }

    public void inativar(EventoNivel eventoNivel) throws ValidationException {
        if (eventoNivel.isAtivo()) {
            eventoNivel.setSituacao(Situacao.inativo);
            saveOrUpdate(eventoNivel);
        }
    }

    public void ativar(EventoNivel eventoNivel) throws ValidationException {
        if (eventoNivel.isInativo()) {
            eventoNivel.setSituacao(Situacao.ativo);
            saveOrUpdate(eventoNivel);
        }
    }

    public EventoNivel findByCodigo(int codigo) {
        return getDAO().findByCodigo(codigo);
    }

    public List<EventoNivel> findListByDescricao(String descricao) {
        return getDAO().findListByDescricao(descricao);
    }
}
