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

import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.Parameter;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import java.util.List;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ViewController
public class EventoTipoMB {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private EventoTipoBC bc;
    
    @Inject
    private MessageContext messageContext;
    
    @Inject
    @Name("messages")
    private ResourceBundle bundle;
    
    @Inject
    private Parameter<Integer> id;
    
    private EventoTipo bean;

    public EventoTipo getBean() {
        if (bean == null) {
            bean = new EventoTipo();
            if(this.id.getValue() != null) { 
                this.bean = bc.load(Long.valueOf(this.id.getValue()));
            }}
        return bean;
    }

    public void setBean(EventoTipo bean) {
        this.bean = bean;
    }

    @Transactional
    public void delete() {
        try {
            this.bc.delete(bean.getId());
            messageContext.add(bundle.getString("delete.sucesso"), SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add(bundle.getString("delete.erro"), SeverityType.ERROR);
        }
    }

    @Transactional
    public void insert() {
        try {
            this.bc.insert(bean);
            messageContext.add(bundle.getString("insert.sucesso"), SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add(bundle.getString("insert.erro"), SeverityType.ERROR);
        }
    }

    @Transactional
    public void update() {
        try {
            this.bc.update(bean);
            messageContext.add(bundle.getString("update.sucesso"), SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add(bundle.getString("update.erro"), SeverityType.ERROR);
        }
    }

    public void getLoad() {
        setBean(this.bc.load(bean.getId()));
    }

    public List<EventoTipo> getList() {
        return this.bc.findAll();
    }
    
    public String handleSelect(SelectEvent e){
        return "/evento_tipo_edit.jsf?faces-redirect=true&id=" + ((EventoTipo) e.getObject()).getId();
    }
    
}