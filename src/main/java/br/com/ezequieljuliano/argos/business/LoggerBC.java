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
package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.config.ArgosMailConfig;
import br.com.ezequieljuliano.argos.domain.Logger;
import br.com.ezequieljuliano.argos.domain.User;
import br.com.ezequieljuliano.argos.domain.UserTerm;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerEntityCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerHostCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerLevelCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerMarkerCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerOccurrenceCount;
import br.com.ezequieljuliano.argos.domain.groupoperation.LoggerOwnerCount;
import br.com.ezequieljuliano.argos.exception.BusinessException;
import br.com.ezequieljuliano.argos.template.StantardBC;
import br.com.ezequieljuliano.argos.persistence.LoggerDAO;
import br.com.ezequieljuliano.argos.util.DateUtil;
import br.com.ezequieljuliano.argos.util.SendMail;
import br.com.ezequieljuliano.argos.util.SendMail.Server;
import br.com.ezequieljuliano.argos.util.VelocityTemplate;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Inject;
import org.apache.commons.mail.EmailException;

@BusinessController
public class LoggerBC extends StantardBC<Logger, String, LoggerDAO> {

    @Inject
    private ArgosMailConfig mailConfig;

    private void notification(Logger logger) {
        SendNotification send = new SendNotification(logger);
        Thread threadDoSendNotification = new Thread(send);
        threadDoSendNotification.start();
    }

    private class SendNotification implements Runnable {

        private final Logger logger;

        public SendNotification(Logger logger) {
            this.logger = logger;
        }

        @Override
        public void run() {
            if (termsOfNotification(logger)) {
                String emailMsg = new VelocityTemplate("template_mail_notification.vm")
                        .set("id", logger.getId())
                        .set("occurrence", DateUtil.dateTimeToString(logger.getOccurrence()))
                        .set("host", logger.getHost())
                        .set("keywords", logger.getKeywords())
                        .set("owner", logger.getOwner())
                        .set("marker", logger.getMarker().getName())
                        .set("level", logger.getLevel().getName())
                        .set("entity", logger.getEntity().getExternalKey() + " - " + logger.getEntity().getName())
                        .set("user", logger.getUser().getUserName() + " - " + logger.getUser().getEmail())
                        .set("message", logger.getMessage())
                        .toString();
                try {
                    new SendMail()
                            .using(new Server()
                                    .hostName(mailConfig.getHostName())
                                    .port(mailConfig.getPort())
                                    .user(mailConfig.getUser())
                                    .passWord(mailConfig.getPassWord())
                                    .withSSL(mailConfig.isSSL()))
                            .from(mailConfig.getUser(), "Argos - Logging")
                            .to(logger.getUser().getEmail(), logger.getUser().getUserName())
                            .subject("Argos - Novo LOG Relevante")
                            .message(emailMsg)
                            .send();
                } catch (EmailException ex) {
                    java.util.logging.Logger.getLogger(UserBC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public Logger save(Logger obj) {
        obj.generateFullText();
        return super.save(obj);
    }

    public Logger validateAndSave(Logger obj) throws BusinessException {
        Logger logger = save(obj);
        if (logger != null) {
            notification(logger);
        }
        return logger;
    }

    public boolean termsOfNotification(Logger logger) {
        return getDelegate().termsOfNotification(logger);
    }

    public List<Logger> findByUserAndTerms(User user, List<UserTerm> terms, long limit) {
        return getDelegate().findByUserAndTerms(user, terms, limit);
    }

    public List<LoggerMarkerCount> groupByMarkerUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        return getDelegate().groupByMarkerUsingUserAndOccurrence(user, startDate, endDate);
    }

    public List<LoggerLevelCount> groupByLevelUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        return getDelegate().groupByLevelUsingUserAndOccurrence(user, startDate, endDate);
    }

    public List<LoggerEntityCount> groupByEntityUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        return getDelegate().groupByEntityUsingUserAndOccurrence(user, startDate, endDate);
    }

    public List<LoggerHostCount> groupByHostUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        return getDelegate().groupByHostUsingUserAndOccurrence(user, startDate, endDate);
    }

    public List<LoggerOwnerCount> groupByOwnerUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        return getDelegate().groupByOwnerUsingUserAndOccurrence(user, startDate, endDate);
    }

    public List<LoggerOccurrenceCount> groupByOccurrenceUsingUserAndOccurrence(User user, Date startDate, Date endDate) {
        return getDelegate().groupByOccurrenceUsingUserAndOccurrence(user, startDate, endDate);
    }

}
