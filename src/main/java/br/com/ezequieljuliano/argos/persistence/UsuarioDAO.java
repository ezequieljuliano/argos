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

import br.com.ezequieljuliano.argos.domain.Usuario;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Repository
public class UsuarioDAO extends GenericDAO<Usuario, String> {

    private static final long serialVersionUID = 1L;

    public Usuario findByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        return getMongoOperations().findOne(query, Usuario.class);
    }

    public List<Usuario> findListByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").regex(userName, "i"));
        return getMongoOperations().find(query, Usuario.class);
    }

    public Usuario findByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return getMongoOperations().findOne(query, Usuario.class);
    }

    public Usuario findByApiKey(String apiKey) {
        Query query = new Query(Criteria.where("apiKey").is(apiKey));
        return getMongoOperations().findOne(query, Usuario.class);
    }

    public Usuario login(String userName, String password) {
        Query query = new Query(Criteria.where("userName").is(userName).and("password").is(password));
        return getMongoOperations().findOne(query, Usuario.class);
    }
}
