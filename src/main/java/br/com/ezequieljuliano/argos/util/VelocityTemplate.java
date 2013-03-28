package br.com.ezequieljuliano.argos.util;

import java.io.StringWriter;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author robson
 */
public class VelocityTemplate {
    
    private String template;
    private VelocityContext context = new VelocityContext();
    private String encoding = "UTF-8";
    
    public VelocityTemplate(String template) {
        this.template = template;
    }
    
    public VelocityTemplate set(String var, Object value){
        context.put(var, value);
        return this;
    }
    
    public VelocityTemplate withEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }
    
    @Override
    public String toString() {
        VelocityEngine engine = VelocityUtil.getVelocityEngine();
        Template t = engine.getTemplate(template, "UTF-8");
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString();
    }
    
}
