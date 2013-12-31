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

import br.com.ezequieljuliano.argos.domain.Marker;
import br.com.ezequieljuliano.argos.template.StandardDAO;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MarkerDAO extends StandardDAO<Marker, String> {

    public List<Marker> findByName(String name) {
        Query query = new Query(Criteria.where("name").regex(name, "i"));
        return getMongoOperations().find(query, Marker.class);
    }

    public Marker findOneByName(String name) {
        Query query = new Query(Criteria.where("name").regex(name));
        return getMongoOperations().findOne(query, Marker.class);
    }

    public List<Marker> findByParent(Marker parent) {
        Query query = new Query(Criteria.where("parent").is(parent));
        return getMongoOperations().find(query, Marker.class);
    }

}
