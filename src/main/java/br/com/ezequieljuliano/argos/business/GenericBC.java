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

import br.com.ezequieljuliano.argos.app.Application;
import br.com.ezequieljuliano.argos.persistence.GenericDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Reflections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@BusinessController
public class GenericBC<DomainType, KeyType, DaoType extends GenericDAO<DomainType, KeyType>> {

    private DaoType dao;
   
    @Inject
    private Application application;

    @PostConstruct
    public void init() {
        dao = (DaoType) application.getContext().getBean(getDaoClass());
    }

    private Class getDaoClass() {
        return Reflections.getGenericTypeArgument(this.getClass(), 2);
    }

    public DomainType load(KeyType id) {
        return dao.load(id);
    }

    public void save(DomainType obj) {
        dao.save(obj);
    }

    public void delete(KeyType id) {
        dao.delete(id);
    }

    public void deleteObj(DomainType obj) {
        dao.deleteObj(obj);
    }

    public List<DomainType> findAll() {
        return dao.findAll();
    }

    protected DaoType getDAO() {
        return dao;
    }
}
