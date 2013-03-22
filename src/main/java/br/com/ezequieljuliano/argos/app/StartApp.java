package br.com.ezequieljuliano.argos.app;

import br.com.ezequieljuliano.argos.business.DadosPadraoBC;
import br.com.ezequieljuliano.argos.exception.ValidationException;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Beans;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@BusinessController
public class StartApp {
    
    @Inject
    private Application application;
    
    @Startup
    public void init(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                "br.com.ezequieljuliano.argos.app",
                "br.com.ezequieljuliano.argos.persistence",
                "br.com.ezequieljuliano.argos.manager");
        application.setContext(context);
        
        DadosPadraoBC dados = Beans.getReference(DadosPadraoBC.class);
        try {
            dados.InsereDados();
        } catch (ValidationException ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(StartApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
