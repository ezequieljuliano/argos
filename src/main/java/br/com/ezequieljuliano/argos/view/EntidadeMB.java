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
package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.EntidadeBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.com.ezequieljuliano.argos.util.JsfUtils;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.util.Parameter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ViewController
public class EntidadeMB {

    private static final long serialVersionUID = 1L;
    @Inject
    private EntidadeBC bc;
    @Inject
    private MessageContext messageContext;
    @Inject
    private Parameter<String> id;
    private Entidade bean = null;
    private List<Entidade> beanList = null;
    private String campoPesquisa = null;

    public Entidade getBean() {
        if (bean == null) {
            bean = new Entidade();
            if (id.getValue() != null) {
                this.bean = bc.load(this.id.getValue());
            }
        }
        return bean;
    }

    public void setBean(Entidade bean) {
        this.bean = bean;
    }

    public List<Entidade> getBeanList() {
        if (beanList == null) {
            this.beanList = bc.findAll();
        }
        return beanList;
    }

    public void setBeanList(List<Entidade> beanList) {
        this.beanList = beanList;
    }

    public String getCampoPesquisa() {
        return campoPesquisa;
    }

    public void setCampoPesquisa(String campoPesquisa) {
        this.campoPesquisa = campoPesquisa;
    }

    public void salvar() {
        try {
            bc.saveOrUpdate(bean);
            messageContext.add("Registro salvo com sucesso!", SeverityType.INFO);
        } catch (ValidationException e) {
            messageContext.add(e.getMessage(), SeverityType.WARN);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao salvar o registro!", SeverityType.ERROR);
        }
    }

    public String deletar() {
        try {
            bc.delete(bean.getId());
            messageContext.add("Registro deletado com sucesso!", SeverityType.INFO);
            return getPreviousView();
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao deletar o registro!", SeverityType.ERROR);
        }
        return "";
    }

    public void inativar() {
        try {
            bc.inativar(bean);
            messageContext.add("Registro inativado com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao inativar o registro!", SeverityType.ERROR);
        }
    }

    public void ativar() {
        try {
            bc.ativar(bean);
            messageContext.add("Registro ativado com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao ativar o registro!", SeverityType.ERROR);
        }
    }

    public List<Entidade> getList() {
        return getBeanList();
    }

    public void findByNome() {
        if (!campoPesquisa.equals("")) {
            this.beanList = bc.findByNome(campoPesquisa);
            if (beanList.isEmpty()) {
                messageContext.add("A pesquisa não retornou nenhum resultado!", SeverityType.WARN);
            }
        } else {
            this.beanList = bc.findAll();
        }
    }

    public void cancelarPesquisa() {
        this.beanList = bc.findAll();
    }

    public void handleSelect(SelectEvent e) {
        try {
            JsfUtils.redireciona("entidade_edit.jsf?faces-redirect=true&id=" + ((Entidade) e.getObject()).getId());
        } catch (Exception ex) {
            Logger.getLogger(EntidadeMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getPreviousView() {
        return "./entidade_list.xhtml";
    }
}
