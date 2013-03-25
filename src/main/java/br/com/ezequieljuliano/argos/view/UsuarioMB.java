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

import br.com.ezequieljuliano.argos.business.UsuarioBC;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioPerfil;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.com.ezequieljuliano.argos.util.JsfUtils;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.util.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ViewController
public class UsuarioMB {

    private static final long serialVersionUID = 1L;
    @Inject
    private UsuarioBC bc;
    @Inject
    private MessageContext messageContext;
    @Inject
    private Parameter<String> id;
    private Usuario bean = null;
    private List<Usuario> beanList = null;
    private String campoPesquisa = null;
    private ArrayList<SelectItem> perfil;

    public Usuario getBean() {
        if (bean == null) {
            bean = new Usuario();
            if (this.id.getValue() != null) {
                this.bean = bc.load(this.id.getValue());
            }
        }
        return bean;
    }

    public void setBean(Usuario bean) {
        this.bean = bean;
    }

    public List<Usuario> getBeanList() {
        if (beanList == null) {
            this.beanList = bc.findAll();
        }
        return beanList;
    }

    public void setBeanList(List<Usuario> beanList) {
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

    public List<Usuario> getList() {
        return getBeanList();
    }

    public void findByUserName() {
        if (!campoPesquisa.equals("")) {
            this.beanList = bc.findListByUserName(campoPesquisa);
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
            JsfUtils.redireciona("usuario_edit.jsf?faces-redirect=true&id=" + ((Usuario) e.getObject()).getId());
        } catch (Exception ex) {
            Logger.getLogger(UsuarioMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<SelectItem> getPerfis() {
        if (this.perfil == null) {
            this.perfil = new ArrayList<SelectItem>();
            for (UsuarioPerfil tipo : UsuarioPerfil.values()) {
                this.perfil.add(new SelectItem(tipo, tipo.getNome()));
            }
        }
        return perfil;
    }

    public void generateApiKey() {
        try {
            bc.generateApiKey(bean);
            bc.saveOrUpdate(bean);
            messageContext.add("API Key gerada com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao gerar a API Key!", SeverityType.ERROR);
        }
    }

    public void trocarSenha() {
        try {
            bc.generatePasswordKey(bean);
            bc.saveOrUpdate(bean);
            messageContext.add("Senha trocada com sucesso!", SeverityType.INFO);
        } catch (Exception e) {
            messageContext.add("Ocorreu um erro ao trocar a senha!", SeverityType.ERROR);
        }
    }

    private String getPreviousView() {
        return "./usuario_list.xhtml";
    }
}
