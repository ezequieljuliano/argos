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
package br.com.ezequieljuliano.argos.persistence;

import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.EventoCampoCustomizado;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class EventoCampoCustomizadoDAO extends BaseDAO<EventoCampoCustomizado, String> {

    private static final long serialVersionUID = 1L;

    public List<EventoCampoCustomizado> findByDescricao(String descricao) {
        String jpql = "select ecc from EventoCampoCustomizado ecc where ecc.descricao = :descricao";
        Query qry = createQuery(jpql);
        qry.setParameter("descricao", descricao);

        List<EventoCampoCustomizado> eventoCampoCustomizadoList = qry.getResultList();
        if (eventoCampoCustomizadoList == null || eventoCampoCustomizadoList.isEmpty()) {
            return new ArrayList<EventoCampoCustomizado>();
        }
        return eventoCampoCustomizadoList;
    }

    public EventoCampoCustomizado findByDescricaoAndEntidade(String descricao, Entidade entidade) {
        String jpql = "select ecc from EventoCampoCustomizado ecc where ecc.descricao = :descricao";
        Query qry = createQuery(jpql);
        qry.setParameter("descricao", descricao);

        List<EventoCampoCustomizado> eventoCampoCustomizadoList = qry.getResultList();
        if (eventoCampoCustomizadoList == null || eventoCampoCustomizadoList.isEmpty()) {
            return null;
        }

        for (EventoCampoCustomizado obj : eventoCampoCustomizadoList) {
            if (obj.getEntidade().equals(entidade)) {
                return obj;
            }
        }

        return null;
    }
}
