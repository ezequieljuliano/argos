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

import br.com.ezequieljuliano.argos.business.UserBC;
import br.com.ezequieljuliano.argos.domain.FilterMatchMode;
import br.com.ezequieljuliano.argos.domain.LogicalOperator;
import br.com.ezequieljuliano.argos.domain.Profile;
import br.com.ezequieljuliano.argos.domain.Term;
import br.com.ezequieljuliano.argos.domain.User;
import br.com.ezequieljuliano.argos.domain.UserTerm;
import br.com.ezequieljuliano.argos.exception.BusinessException;
import br.com.ezequieljuliano.argos.template.StandardMB;
import br.com.ezequieljuliano.argos.util.HashUtil;
import br.com.ezequieljuliano.argos.util.JSFUtil;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

@ViewController
public class UserMB extends StandardMB<User, String> {

    @Inject
    private UserBC bc;

    private UserTerm userTerm;

    @Override
    protected List<User> handleResultList() {
        return bc.findAll();
    }

    @Override
    protected User handleLoad(String id) {
        return bc.load(id);
    }

    @Override
    public void save() {
        try {
            bc.validateAndSave(getBean());
            addMessageContext("Registro salvo com sucesso!", SeverityType.INFO);
        } catch (BusinessException ex) {
            addMessageContext(ex.getMessage(), SeverityType.WARN);
        } catch (Exception ex) {
            addMessageContext(ex.getMessage(), SeverityType.ERROR);
        }
    }

    @Override
    public void delete() {
        try {
            bc.delete(getId());
            addMessageContext("Registro deletado com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            addMessageContext("Ocorreu um erro ao deletar o registro!", SeverityType.ERROR);
        }
        redirectListView();
    }

    @Override
    public void deleteSelection() {
        try {
            boolean delete;
            for (Iterator<String> iter = getSelection().keySet().iterator(); iter.hasNext();) {
                String id = iter.next();
                delete = getSelection().get(id);
                if (delete) {
                    bc.delete(id);
                    iter.remove();
                }
            }
            addMessageContext("Registro deletado com sucesso!", SeverityType.INFO);
            redirectListView();
        } catch (Exception e) {
            addMessageContext("Ocorreu um erro ao deletar o registro!", SeverityType.ERROR);
        }
    }

    @Override
    public List<User> getResultList() {
        return super.getResultList();
    }

    @Override
    public void redirectEditView(String id) {
        try {
            String url = "user_edit.jsf?faces-redirect=true";
            if (!Strings.isEmpty(id)) {
                url = url + "&id=" + id;
            }
            JSFUtil.redirect(url);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserMB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public void redirectListView() {
        try {
            JSFUtil.redirect("user_list.jsf?faces-redirect=true");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(UserMB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isInsertMode() {
        return getBean().getId() == null;
    }

    public void activate() {
        try {
            bc.activate(getBean());
            addMessageContext("Registro ativado com sucesso!", SeverityType.INFO);
        } catch (BusinessException ex) {
            addMessageContext(ex.getMessage(), SeverityType.WARN);
        }
    }

    public void inactivate() {
        try {
            bc.inactivate(getBean());
            addMessageContext("Registro inativado com sucesso!", SeverityType.INFO);
        } catch (BusinessException ex) {
            addMessageContext(ex.getMessage(), SeverityType.WARN);
        }
    }

    public Profile[] getProfiles() {
        return Profile.values();
    }

    public void generateIdentifierKey() {
        try {
            getBean().generateIdentifierKey();
            bc.validateAndSave(getBean());
            addMessageContext("Chave identificadora trocada com sucesso!", SeverityType.INFO);
        } catch (BusinessException ex) {
            addMessageContext(ex.getMessage(), SeverityType.WARN);
        }
    }

    public void alterPassWord() {
        try {
            String passWord = getBean().getPassWord();
            getBean().setPassWord(HashUtil.generateHash(passWord, HashUtil.SHA1));
            bc.validateAndSave(getBean());
            addMessageContext("Senha alterada com sucesso!", SeverityType.INFO);
        } catch (BusinessException ex) {
            addMessageContext(ex.getMessage(), SeverityType.WARN);
        }
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

    public Term[] getTerms() {
        return Term.values();
    }

    public FilterMatchMode[] getFiltersMatchMode() {
        return FilterMatchMode.values();
    }

    public LogicalOperator[] getLogicalOperators() {
        return LogicalOperator.values();
    }

    public void addUserTerm() {
        if (!Strings.isEmpty(getUserTerm().getValue())) {
            if (isFullTextTerm() && !isContainsFilterMatchMode()) {
                addMessageContext("Quando selecionado o Termo 'Tudo' o Modo deve ser 'Contém'!", SeverityType.WARN);
            } else {
                if (getBean().getTerms().isEmpty() && getUserTerm().getLogicalOperator() == LogicalOperator.orOperator) {
                    addMessageContext("Quando não existem termos o 'Operador' deve ser 'E'!", SeverityType.WARN);
                } else {
                    if (getUserTerm().getTerm().isObjId() && getUserTerm().getFilterMatchMode() != FilterMatchMode.equal) {
                        addMessageContext("Quando selecionado um Termo que é identificador você deve usar o Modo 'Igual'!", SeverityType.WARN);
                    } else {
                        try {
                            getBean().addTerm(getUserTerm());
                            bc.validateAndSave(getBean());
                            setUserTerm(null);
                            addMessageContext("Termo adicionado e salvo com sucesso!", SeverityType.INFO);
                        } catch (BusinessException ex) {
                            addMessageContext(ex.getMessage(), SeverityType.WARN);
                        }
                    }
                }
            }
        } else {
            addMessageContext("Você deve informar um valor para o termo!", SeverityType.WARN);
        }
    }

    public void removeUserTerm(UserTerm ut) {
        if (ut != null) {
            try {
                getBean().removeTerm(ut);
                bc.validateAndSave(getBean());
                addMessageContext("Termo removido com sucesso!", SeverityType.INFO);
            } catch (BusinessException ex) {
                addMessageContext(ex.getMessage(), SeverityType.WARN);
            }
        }
    }

    public boolean isFullTextTerm() {
        return getUserTerm().getTerm() == Term.fullText;
    }

    public boolean isContainsFilterMatchMode() {
        return getUserTerm().getFilterMatchMode() == FilterMatchMode.contains;
    }

}
