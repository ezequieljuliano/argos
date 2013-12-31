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
package br.com.ezequieljuliano.argos.persistence;

import br.com.ezequieljuliano.argos.domain.Entity;
import br.com.ezequieljuliano.argos.template.StandardDAO;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class EntityDAO extends StandardDAO<Entity, String> {

    public List<Entity> findByName(String name) {
        Query query = new Query(Criteria.where("name").regex(name, "i"));
        return getMongoOperations().find(query, Entity.class);
    }

    public Entity findByExternalKey(String externalKey) {
        Query query = new Query(Criteria.where("externalKey").is(externalKey));
        return getMongoOperations().findOne(query, Entity.class);
    }

    public List<Entity> findByParent(Entity parent) {
        Query query = new Query(Criteria.where("parent").is(parent));
        return getMongoOperations().find(query, Entity.class);
    }

}
