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

import br.gov.frameworkdemoiselle.util.Reflections;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class StandardDAO<DomainClass, KeyType> {

    @Autowired
    private MongoOperations mongoOperations;

    private Class<DomainClass> domainClass;

    private CommandResult executeFullTextSearch(String collectionName, String searchString, Criteria filterCriteria, long limit) {
        BasicDBObject textSearch = new BasicDBObject();
        textSearch.put("text", collectionName);
        textSearch.put("search", searchString);
        if (filterCriteria != null) {
            textSearch.put("filter", Query.query(filterCriteria).getQueryObject());
        }
        if (limit > 0) {
            textSearch.put("limit", limit);
        }
        textSearch.put("project", new BasicDBObject("_id", 1));
        return getMongoOperations().executeCommand(textSearch);
    }

    private Collection<ObjectId> extractSearchResultIds(CommandResult commandResult) {
        Set<ObjectId> objectIds = new HashSet<ObjectId>();
        BasicDBList resultList = (BasicDBList) commandResult.get("results");
        if (resultList == null) {
            return objectIds;
        }
        Iterator<Object> it = resultList.iterator();
        while (it.hasNext()) {
            BasicDBObject resultContainer = (BasicDBObject) it.next();
            BasicDBObject resultObject = (BasicDBObject) resultContainer.get("obj");
            ObjectId resultId = (ObjectId) resultObject.get("_id");
            objectIds.add(resultId);
        }
        return objectIds;
    }

    protected MongoOperations getMongoOperations() {
        return this.mongoOperations;
    }

    protected Class<DomainClass> getDomainClass() {
        if (this.domainClass == null) {
            this.domainClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
        }
        return this.domainClass;
    }

    public DomainClass save(final DomainClass obj) {
        getMongoOperations().save(obj);
        return obj;
    }

    public void delete(final KeyType id) {
        DomainClass obj = load(id);
        getMongoOperations().remove(obj);
    }

    public void deleteObj(final DomainClass obj) {
        getMongoOperations().remove(obj);
    }

    public DomainClass load(final KeyType id) {
        return (DomainClass) getMongoOperations().findById(id, getDomainClass());
    }

    public List<DomainClass> findAll() {
        return getMongoOperations().findAll(getDomainClass());
    }

    public List<DomainClass> findByFullText(String collectionName, String searchString, Criteria filterCriteria, long limit) {
        CommandResult commandResult = executeFullTextSearch(collectionName, searchString, filterCriteria, limit);
        Collection<ObjectId> searchResultIds = extractSearchResultIds(commandResult);
        Query mongoQuery = Query.query(Criteria.where("_id").in(searchResultIds));
        return getMongoOperations().find(mongoQuery, getDomainClass());
    }

    public long countAll() {
        return getMongoOperations().count(new Query(), getDomainClass());
    }

    public boolean collectionExists() {
        return getMongoOperations().collectionExists(getDomainClass());
    }

    public void removeAll() {
        getMongoOperations().remove(new Query(), getDomainClass());
    }

}
