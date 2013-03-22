package br.com.ezequieljuliano.argos.app;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import org.springframework.context.ConfigurableApplicationContext;

@ApplicationScoped
public class Application implements Serializable {
    
    private ConfigurableApplicationContext context;

    public ConfigurableApplicationContext getContext() {
        return context;
    }

    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }
    
    
}
