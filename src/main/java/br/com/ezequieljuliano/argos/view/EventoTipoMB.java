package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import java.util.List;
import javax.inject.Inject;

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
    
    private EventoTipo bean;

    public EventoTipo getBean() {
        if (bean == null) {
            bean = new EventoTipo();
        }
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
}