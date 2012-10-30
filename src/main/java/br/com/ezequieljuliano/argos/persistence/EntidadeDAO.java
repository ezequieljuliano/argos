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
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class EntidadeDAO extends BaseDAO<Entidade, String> {

    private static final long serialVersionUID = 1L;

    public Entidade findByCodigo(int codigo) {
        String jpql = "select e from Entidade e where e.codigo = :codigo";
        Query qry = createQuery(jpql);
        qry.setParameter("codigo", codigo);
        List<Entidade> entidadeList = qry.getResultList();
        if (entidadeList == null || entidadeList.isEmpty()) {
            return null;
        }
        return entidadeList.get(0);
    }

    public Entidade findByCadastroNacional(String cadastroNacional) {
        String jpql = "select e from Entidade e where e.cadastroNacional = :cadastroNacional";
        Query qry = createQuery(jpql);
        qry.setParameter("cadastroNacional", cadastroNacional);
        List<Entidade> entidadeList = qry.getResultList();
        if (entidadeList == null || entidadeList.isEmpty()) {
            return null;
        }
        return entidadeList.get(0);
    }
}
