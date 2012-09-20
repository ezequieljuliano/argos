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

import br.gov.frameworkdemoiselle.template.JPACrud;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class BaseDAO<DomainType, KeyType> extends JPACrud<DomainType, KeyType> {

    private static final long serialVersionUID = 1L;
    
    @Override
    public List<DomainType> findAll() {
        Query qry = createQuery("select this from " + getBeanClass().getSimpleName() + " this");
        return qry.getResultList();
    }
}
