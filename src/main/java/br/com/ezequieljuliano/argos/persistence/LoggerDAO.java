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
import br.com.ezequieljuliano.argos.domain.Logger;
import br.com.ezequieljuliano.argos.domain.LogicalOperator;
import br.com.ezequieljuliano.argos.domain.Profile;
import br.com.ezequieljuliano.argos.domain.Term;
import br.com.ezequieljuliano.argos.domain.User;
import br.com.ezequieljuliano.argos.domain.UserTerm;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerEntityCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerHostCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerLevelCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerMarkerCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerOccurrenceCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerOwnerCount;
import br.com.ezequieljuliano.argos.template.StandardDAO;
import br.gov.frameworkdemoiselle.util.Strings;
import com.mongodb.BasicDBObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LoggerDAO extends StandardDAO<Logger, String> {

    @Autowired
    private UserDAO userDAO;

    //Método temporário devido a bug do SpringDataMongoDB
    private List<ObjectId> findEntityObjectIdsByUser(User user) {
        List<Entity> entitysTreeOfUser = userDAO.findEntitysTreeByUser(user);

        List<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (Entity e : entitysTreeOfUser) {
            objectIds.add(new ObjectId(e.getId()));
        }

        return objectIds;
    }

    private List<Logger> findByTermsAndCriteria(List<UserTerm> terms, Criteria criteria, long limit) {
        List<Logger> loggers = new ArrayList<Logger>();
        if (!terms.isEmpty()) {
            String searchString = "";
            for (UserTerm userTerm : terms) {
                if (userTerm.getTerm() == Term.fullText) {
                    searchString = searchString + userTerm.getValue() + " ";
                } else {
                    switch (userTerm.getFilterMatchMode()) {
                        case contains:
                            if (userTerm.getLogicalOperator() == LogicalOperator.orOperator) {
                                criteria.orOperator(Criteria.where(userTerm.getTerm().getField()).regex(userTerm.getValue(), "i"));
                            } else {
                                criteria.and(userTerm.getTerm().getField()).regex(userTerm.getValue(), "i");
                            }
                            break;
                        case startsWith:
                            if (userTerm.getLogicalOperator() == LogicalOperator.orOperator) {
                                criteria.orOperator(Criteria.where(userTerm.getTerm().getField()).regex('^' + userTerm.getValue(), "i"));
                            } else {
                                criteria.and(userTerm.getTerm().getField()).regex('^' + userTerm.getValue(), "i");
                            }
                            break;
                        case equal:
                            if (userTerm.getLogicalOperator() == LogicalOperator.orOperator) {
                                if (userTerm.getTerm().isObjId()) {
                                    criteria.orOperator(Criteria.where(userTerm.getTerm().getField()).is(new ObjectId(userTerm.getValue())));
                                } else {
                                    criteria.orOperator(Criteria.where(userTerm.getTerm().getField()).is(userTerm.getValue()));
                                }
                            } else {
                                if (userTerm.getTerm().isObjId()) {
                                    criteria.and(userTerm.getTerm().getField()).is(new ObjectId(userTerm.getValue()));
                                } else {
                                    criteria.and(userTerm.getTerm().getField()).is(userTerm.getValue());
                                }
                            }
                            break;
                        case endsWith:
                            if (userTerm.getLogicalOperator() == LogicalOperator.orOperator) {
                                criteria.orOperator(Criteria.where(userTerm.getTerm().getField()).regex(userTerm.getValue() + '$', "i"));
                            } else {
                                criteria.and(userTerm.getTerm().getField()).regex(userTerm.getValue() + '$', "i");
                            }
                            break;
                    }
                }
            }
            if (Strings.isEmpty(searchString)) {
                loggers = getMongoOperations().find(Query.query(criteria).limit((int) limit), Logger.class);
            } else {
                loggers = findByFullText(Logger.COLLECTION_NAME, searchString, criteria, limit);
            }
        }
        return loggers;
    }

    @PostConstruct
    public void ensureFullTextIndex() {
        // make sure the index is set up properly (not yet possible via Spring Data Annotations)
        getMongoOperations().getCollection(Logger.COLLECTION_NAME).ensureIndex(new BasicDBObject("fullText", "text"));
    }

    public boolean termsOfNotification(Logger logger) {
        Criteria criteria = Criteria.where("_id").is(new ObjectId(logger.getId()));
        List<Logger> loggers = findByTermsAndCriteria(logger.getUser().getTerms(), criteria, 1);
        return !loggers.isEmpty();
    }

    public List<Logger> findByUserAndTerms(User user, List<UserTerm> terms, long limit) {
        if (user.haveProfile(Profile.admin)) {
            Criteria criteria = Criteria.where("_id").ne(null);
            return findByTermsAndCriteria(terms, criteria, limit);
        } else {
            //List<Entity> entitysTreeOfUser = userDAO.findEntitysTreeByUser(user);
            //Criteria criteria = Criteria.where("entity").in(entitysTreeOfUser);
            //return findByTermsAndCriteria(terms, criteria, limit);
            //Método temporário devido a bug do SpringDataMongoDB            
            List<ObjectId> objectIds = findEntityObjectIdsByUser(user);
            Criteria criteria = Criteria.where("entity._id").in(objectIds);
            return findByTermsAndCriteria(terms, criteria, limit);
        }
    }

    public List<LoggerMarkerCount> groupByMarkerUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        Criteria criteria = Criteria.where("entity._id").in(findEntityObjectIdsByUser(user))
                .andOperator(
                        Criteria.where("occurrence").lte(endDate),
                        Criteria.where("occurrence").gte(startDate)
                );

        GroupByResults<LoggerMarkerCount> results = getMongoOperations().group(criteria, Logger.COLLECTION_NAME,
                GroupBy.key("marker").initialDocument("{ count: 0 }").reduceFunction("function(doc, prev) { prev.count += 1 }"),
                LoggerMarkerCount.class);

        List<LoggerMarkerCount> counts = new ArrayList<LoggerMarkerCount>();
        for (LoggerMarkerCount marker : results) {
            counts.add(marker);
        }

        return counts;
    }

    public List<LoggerLevelCount> groupByLevelUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        Criteria criteria = Criteria.where("entity._id").in(findEntityObjectIdsByUser(user))
                .andOperator(
                        Criteria.where("occurrence").lte(endDate),
                        Criteria.where("occurrence").gte(startDate)
                );

        GroupByResults<LoggerLevelCount> results = getMongoOperations().group(criteria, Logger.COLLECTION_NAME,
                GroupBy.key("level").initialDocument("{ count: 0 }").reduceFunction("function(doc, prev) { prev.count += 1 }"),
                LoggerLevelCount.class);

        List<LoggerLevelCount> counts = new ArrayList<LoggerLevelCount>();
        for (LoggerLevelCount level : results) {
            counts.add(level);
        }

        return counts;
    }

    public List<LoggerEntityCount> groupByEntityUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        Criteria criteria = Criteria.where("entity._id").in(findEntityObjectIdsByUser(user))
                .andOperator(
                        Criteria.where("occurrence").lte(endDate),
                        Criteria.where("occurrence").gte(startDate)
                );

        GroupByResults<LoggerEntityCount> results = getMongoOperations().group(criteria, Logger.COLLECTION_NAME,
                GroupBy.key("entity").initialDocument("{ count: 0 }").reduceFunction("function(doc, prev) { prev.count += 1 }"),
                LoggerEntityCount.class);

        List<LoggerEntityCount> counts = new ArrayList<LoggerEntityCount>();
        for (LoggerEntityCount entity : results) {
            counts.add(entity);
        }

        return counts;
    }

    public List<LoggerHostCount> groupByHostUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        Criteria criteria = Criteria.where("entity._id").in(findEntityObjectIdsByUser(user))
                .andOperator(
                        Criteria.where("occurrence").lte(endDate),
                        Criteria.where("occurrence").gte(startDate)
                );

        GroupByResults<LoggerHostCount> results = getMongoOperations().group(criteria, Logger.COLLECTION_NAME,
                GroupBy.key("host").initialDocument("{ count: 0 }").reduceFunction("function(doc, prev) { prev.count += 1 }"),
                LoggerHostCount.class);

        List<LoggerHostCount> counts = new ArrayList<LoggerHostCount>();
        for (LoggerHostCount host : results) {
            counts.add(host);
        }

        return counts;
    }

    public List<LoggerOwnerCount> groupByOwnerUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        Criteria criteria = Criteria.where("entity._id").in(findEntityObjectIdsByUser(user))
                .andOperator(
                        Criteria.where("occurrence").lte(endDate),
                        Criteria.where("occurrence").gte(startDate)
                );

        GroupByResults<LoggerOwnerCount> results = getMongoOperations().group(criteria, Logger.COLLECTION_NAME,
                GroupBy.key("owner").initialDocument("{ count: 0 }").reduceFunction("function(doc, prev) { prev.count += 1 }"),
                LoggerOwnerCount.class);

        List<LoggerOwnerCount> counts = new ArrayList<LoggerOwnerCount>();
        for (LoggerOwnerCount owner : results) {
            counts.add(owner);
        }

        return counts;
    }

    public List<LoggerOccurrenceCount> groupByOccurrenceUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        Criteria criteria = Criteria.where("entity._id").in(findEntityObjectIdsByUser(user))
                .andOperator(
                        Criteria.where("occurrence").lte(endDate),
                        Criteria.where("occurrence").gte(startDate)
                );

        GroupByResults<LoggerOccurrenceCount> results = getMongoOperations().group(criteria, Logger.COLLECTION_NAME,
                GroupBy.key("occurrence").initialDocument("{ count: 0 }").reduceFunction("function(doc, prev) { prev.count += 1 }"),
                LoggerOccurrenceCount.class);

        List<LoggerOccurrenceCount> counts = new ArrayList<LoggerOccurrenceCount>();
        for (LoggerOccurrenceCount occurrence : results) {
            counts.add(occurrence);
        }

        return counts;
    }

}
