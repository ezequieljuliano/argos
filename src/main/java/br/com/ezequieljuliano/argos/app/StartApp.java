package br.com.ezequieljuliano.argos.app;

import br.com.ezequieljuliano.argos.business.DadosPadraoBC;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Beans;
import javax.inject.Inject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@BusinessController
public class StartApp {

    @Inject
    private Application application;

    @Startup
    public void init() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                "br.com.ezequieljuliano.argos.app",
                "br.com.ezequieljuliano.argos.persistence",
                "br.com.ezequieljuliano.argos.manager");

        application.setContext(context);

        DadosPadraoBC dados = Beans.getReference(DadosPadraoBC.class);
        dados.InsereDados();
    }
}
