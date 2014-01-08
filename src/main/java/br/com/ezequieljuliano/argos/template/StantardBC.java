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
package br.com.ezequieljuliano.argos.template;

import br.com.ezequieljuliano.argos.config.AppContext;
import br.gov.frameworkdemoiselle.util.Reflections;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class StantardBC<DomainClass, KeyType, DelegateClass extends StandardDAO<DomainClass, KeyType>> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Class<DelegateClass> delegateClass;

    private DelegateClass delegate;

    @Inject
    private AppContext appContext;

    @PostConstruct
    public void init() {
        this.delegate = (DelegateClass) appContext.getContext().getBean(getDelegateClass());
    }

    private Class<DelegateClass> getDelegateClass() {
        if (this.delegateClass == null) {
            this.delegateClass = Reflections.getGenericTypeArgument(this.getClass(), 2);
        }
        return this.delegateClass;
    }

    public DelegateClass getDelegate() {
        return this.delegate;
    }

    public DomainClass save(final DomainClass obj) {
        return getDelegate().save(obj);
    }

    public void delete(final KeyType id) {
        getDelegate().delete(id);
    }

    public void deleteObj(final DomainClass obj) {
        getDelegate().deleteObj(obj);
    }

    public DomainClass load(final KeyType id) {
        return (DomainClass) getDelegate().load(id);
    }

    public List<DomainClass> findAll() {
        return getDelegate().findAll();
    }

}
