package br.com.ezequieljuliano.argos.util;

import br.gov.frameworkdemoiselle.util.Beans;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

public class EntityManagerUtil implements Serializable {
    
    public Connection getEntityManagerConnection(){
        EntityManager entityManager =  Beans.getReference(EntityManager.class);
        entityManager.getTransaction().begin();
        Connection jdbcConn = entityManager.unwrap(Connection.class);
        entityManager.getTransaction().commit();
        return jdbcConn;
    }
    
    public Connection getNewConnection(){
        EntityManager entityManager =  Beans.getReference(EntityManager.class);
        String driver = (String) entityManager.getProperties().get("javax.persistence.jdbc.driver");
        String user = (String) entityManager.getProperties().get("javax.persistence.jdbc.user");
        String pass = (String) entityManager.getProperties().get("javax.persistence.jdbc.password");
        String url = (String) entityManager.getProperties().get("javax.persistence.jdbc.url");
        Connection jdbcConn = null;
        try {
            Class.forName(driver);
            jdbcConn = DriverManager.getConnection(url, user, pass);
        } catch (Exception ex) {
            Logger.getLogger(EntityManagerUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jdbcConn;
    }
}
