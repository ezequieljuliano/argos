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

import br.com.ezequieljuliano.argos.business.LevelBC;
import br.com.ezequieljuliano.argos.domain.Level;
import br.com.ezequieljuliano.argos.exception.BusinessException;
import br.com.ezequieljuliano.argos.template.StandardMB;
import br.com.ezequieljuliano.argos.util.JSFUtil;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

@ViewController
public class LevelMB extends StandardMB<Level, String> {

    @Inject
    private LevelBC bc;

    @Override
    protected List<Level> handleResultList() {
        return bc.findAll();
    }

    @Override
    protected Level handleLoad(String id) {
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
    public List<Level> getResultList() {
        return super.getResultList();
    }

    @Override
    public void redirectEditView(String id) {
        try {
            String url = "level_edit.jsf?faces-redirect=true";
            if (!Strings.isEmpty(id)) {
                url = url + "&id=" + id;
            }
            JSFUtil.redirect(url);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LevelMB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public void redirectListView() {
        try {
            JSFUtil.redirect("level_list.jsf?faces-redirect=true");
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(LevelMB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isInsertMode() {
        return getBean().getId() == null;
    }

}
