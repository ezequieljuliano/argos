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

import br.gov.frameworkdemoiselle.util.Reflections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Repository
public class GenericDAO<DomainType, KeyType> {

    @Autowired
    private MongoOperations mongoOperations;

    public DomainType load(KeyType id) {
        return (DomainType) mongoOperations.findById(id, getDomainClass());
    }

    public void insert(DomainType obj) {
        mongoOperations.insert(obj);
    }

    public void save(DomainType obj) {
        mongoOperations.save(obj);
    }

    public void delete(KeyType id) {
        DomainType obj = load(id);
        mongoOperations.remove(obj);
    }

    public void deleteObj(DomainType obj) {
        mongoOperations.remove(obj);
    }

    public List<DomainType> findAll() {
        return mongoOperations.findAll(getDomainClass());
    }

    protected Class getDomainClass() {
        return Reflections.getGenericTypeArgument(this.getClass(), 0);
    }

    protected MongoOperations getMongoOperations() {
        return mongoOperations;
    }
}
