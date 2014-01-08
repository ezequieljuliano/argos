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
import br.com.ezequieljuliano.argos.domain.User;
import br.com.ezequieljuliano.argos.template.StandardDAO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends StandardDAO<User, String> {

    @Autowired
    private EntityDAO entityDAO;

    public List<User> findByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").regex(userName, "i"));
        return getMongoOperations().find(query, User.class);
    }

    public User findOneByUserName(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        return getMongoOperations().findOne(query, User.class);
    }

    public List<User> findByEmail(String email) {
        Query query = new Query(Criteria.where("email").regex(email, "i"));
        return getMongoOperations().find(query, User.class);
    }

    public User findOneByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return getMongoOperations().findOne(query, User.class);
    }

    public User findByIdentifierKey(String identifierKey) {
        Query query = new Query(Criteria.where("identifierKey").is(identifierKey));
        return getMongoOperations().findOne(query, User.class);
    }

    public User findByUserNameAndPassWord(String userName, String passWord) {
        Query query = new Query(Criteria.where("userName").is(userName).and("passWord").is(passWord));
        return getMongoOperations().findOne(query, User.class);
    }

    public List<Entity> findEntitysTreeByUser(User user) {
        List<Entity> entitys = new ArrayList<Entity>();
        entityDAO.findEntitysTree(user.getEntity(), entitys);
        return entitys;
    }

}
