package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.app.Application;
import br.com.ezequieljuliano.argos.persistence.GenericDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Reflections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

@BusinessController
public class GenericBC<DomainType, KeyType, DaoType extends GenericDAO<DomainType, KeyType>> {

    private DaoType dao;
   
    @Inject
    private Application application;

    @PostConstruct
    public void init() {
        dao = (DaoType) application.getContext().getBean(getDaoClass());
    }

    private Class getDaoClass() {
        return Reflections.getGenericTypeArgument(this.getClass(), 2);
    }

    public DomainType load(KeyType id) {
        return dao.load(id);
    }

    public void save(DomainType obj) {
        dao.save(obj);
    }

    public void delete(KeyType id) {
        dao.delete(id);
    }

    public void deleteObj(DomainType obj) {
        dao.deleteObj(obj);
    }

    public List<DomainType> findAll() {
        return dao.findAll();
    }

    protected DaoType getDAO() {
        return dao;
    }
}
