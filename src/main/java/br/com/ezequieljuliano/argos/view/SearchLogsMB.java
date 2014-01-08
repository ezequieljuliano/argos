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
package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.LoggerBC;
import br.com.ezequieljuliano.argos.domain.FilterMatchMode;
import br.com.ezequieljuliano.argos.domain.Logger;
import br.com.ezequieljuliano.argos.domain.LogicalOperator;
import br.com.ezequieljuliano.argos.domain.Term;
import br.com.ezequieljuliano.argos.domain.UserTerm;
import br.com.ezequieljuliano.argos.security.AppCredential;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

@ViewController
public class SearchLogsMB implements Serializable {

    @Inject
    private MessageContext messageContext;

    @Inject
    private AppCredential credential;

    @Inject
    private LoggerBC loggerBC;

    private List<Logger> logs = new ArrayList<Logger>();
    private UserTerm userTerm;
    private List<UserTerm> userTermList = new ArrayList<UserTerm>();
    private Logger logDetail = new Logger();

    public SearchLogsMB() {

    }

    public List<Logger> getLogs() {
        return logs;
    }

    public Term[] getTerms() {
        return Term.values();
    }

    public FilterMatchMode[] getFiltersMatchMode() {
        return FilterMatchMode.values();
    }

    public LogicalOperator[] getLogicalOperators() {
        return LogicalOperator.values();
    }

    public UserTerm getUserTerm() {
        if (userTerm == null) {
            userTerm = new UserTerm();
        }
        return userTerm;
    }

    public void setUserTerm(UserTerm userTerm) {
        this.userTerm = userTerm;
    }

    public List<UserTerm> getUserTermList() {
        return userTermList;
    }

    public Logger getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(Logger logDetail) {
        this.logDetail = logDetail;
    }

    public void addUserTerm() {
        if (!Strings.isEmpty(getUserTerm().getValue())) {
            if (isFullTextTerm() && !isContainsFilterMatchMode()) {
                messageContext.add("Quando selecionado o Termo 'Tudo' o Modo deve ser 'Contém'!", SeverityType.WARN);
            } else {
                if (getUserTermList().isEmpty() && getUserTerm().getLogicalOperator() == LogicalOperator.orOperator) {
                    messageContext.add("Quando não existem termos o 'Operador' deve ser 'E'!", SeverityType.WARN);
                } else {
                    if (getUserTerm().getTerm().isObjId() && getUserTerm().getFilterMatchMode() != FilterMatchMode.equal) {
                        messageContext.add("Quando selecionado um Termo que é identificador você deve usar o Modo 'Igual'!", SeverityType.WARN);
                    } else {
                        try {
                            getUserTermList().add(getUserTerm());
                            setUserTerm(null);
                            messageContext.add("Termo adicionado e salvo com sucesso!", SeverityType.INFO);
                        } catch (RuntimeException ex) {
                            messageContext.add(ex.getMessage(), SeverityType.WARN);
                        }
                    }
                }
            }
        } else {
            messageContext.add("Você deve informar um valor para o termo!", SeverityType.WARN);
        }
    }

    public void removeUserTerm(UserTerm ut) {
        if (ut != null) {
            try {
                getUserTermList().remove(ut);
                messageContext.add("Termo removido com sucesso!", SeverityType.INFO);
            } catch (RuntimeException ex) {
                messageContext.add(ex.getMessage(), SeverityType.WARN);
            }
        }
    }

    public boolean isFullTextTerm() {
        return getUserTerm().getTerm() == Term.fullText;
    }

    public boolean isContainsFilterMatchMode() {
        return getUserTerm().getFilterMatchMode() == FilterMatchMode.contains;
    }

    public void executeSearch() {
        if (getUserTermList().isEmpty()) {
            messageContext.add("É necessário existir algum Termo para efetuar uma pesquisa! Informe-os!", SeverityType.WARN);
        } else {
            this.logs = loggerBC.findByUserAndTerms(credential.getUser(), getUserTermList(), 100);
        }
    }

    public boolean isEmptyLogs() {
        return getLogs().isEmpty();
    }

}
