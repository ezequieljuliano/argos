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

import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.pagination.Pagination;
import br.gov.frameworkdemoiselle.pagination.PaginationContext;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Faces;
import br.gov.frameworkdemoiselle.util.Parameter;
import br.gov.frameworkdemoiselle.util.Reflections;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;

public abstract class StandardMB<DomainClass, KeyType> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private MessageContext messageContext;

    @Inject
    private PaginationContext paginationContext;

    @Inject
    private Parameter<String> id;

    private DomainClass bean;

    private String nextView;

    private String previousView;

    private List<DomainClass> resultList;

    private transient DataModel<DomainClass> dataModel;

    private Map<KeyType, Boolean> selection = new HashMap<KeyType, Boolean>();

    private Class<DomainClass> beanClass;

    private Class<KeyType> idClass;

    protected Class<DomainClass> getBeanClass() {
        if (beanClass == null) {
            beanClass = Reflections.getGenericTypeArgument(this.getClass(), 0);
        }
        return beanClass;
    }

    protected Class<KeyType> getIdClass() {
        if (idClass == null) {
            idClass = Reflections.getGenericTypeArgument(this.getClass(), 1);
        }
        return idClass;
    }

    protected abstract List<DomainClass> handleResultList();

    protected abstract DomainClass handleLoad(final KeyType id);

    public abstract void save();

    public abstract void delete();

    public abstract void deleteSelection();

    public abstract void redirectEditView(final KeyType id);

    public abstract void redirectListView();

    public abstract boolean isInsertMode();

    public DataModel<DomainClass> getDataModel() {
        if (dataModel == null) {
            dataModel = new ListDataModel<DomainClass>(getResultList());
        }
        return this.dataModel;
    }

    public List<DomainClass> getResultList() {
        if (resultList == null) {
            resultList = handleResultList();
        }
        return resultList;
    }

    public void setResultList(List<DomainClass> resultList) {
        this.resultList = resultList;
    }

    public Map<KeyType, Boolean> getSelection() {
        return selection;
    }

    public void setSelection(Map<KeyType, Boolean> selection) {
        this.selection = selection;
    }

    public void clearSelection() {
        selection = new HashMap<KeyType, Boolean>();
    }

    public List<KeyType> getSelectedList() {
        List<KeyType> selectedList = new ArrayList<KeyType>();
        Iterator<KeyType> iter = getSelection().keySet().iterator();
        while (iter.hasNext()) {
            KeyType idKey = iter.next();
            if (getSelection().get(idKey)) {
                selectedList.add(idKey);
            }
        }
        return selectedList;
    }

    public Pagination getPagination() {
        return paginationContext.getPagination(getBeanClass(), true);
    }

    protected DomainClass createBean() {
        return Beans.getReference(getBeanClass());
    }

    public DomainClass getBean() {
        if (bean == null) {
            initBean();
        }
        return bean;
    }

    private void initBean() {
        if (getId() != null) {
            bean = loadBean();
        } else {
            setBean(createBean());
        }
    }

    private DomainClass loadBean() {
        return handleLoad(getId());
    }

    protected void setBean(final DomainClass bean) {
        this.bean = bean;
    }

    private Converter getIdConverter() {
        return Faces.getConverter(getIdClass());
    }

    public KeyType getId() {
        Converter converter = getIdConverter();
        if (converter == null && String.class.equals(getIdClass())) {
            return (KeyType) this.id.getValue();
        } else if (converter == null) {
            return null;
        } else {
            return (KeyType) converter.getAsObject(facesContext, facesContext.getViewRoot(), this.id.getValue());
        }
    }

    public void clearListView() {
        this.dataModel = null;
        this.resultList = null;
    }

    public void clearEditView() {
        this.id = null;
        this.bean = null;
    }

    public void addMessageContext(String string, SeverityType st) {
        messageContext.add(string, st);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().
                setKeepMessages(true);
    }

}
