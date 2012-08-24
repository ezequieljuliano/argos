package br.com.ezequieljuliano.argos.util;

import java.io.IOException;
import java.io.OutputStream;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public class JsfUtils {
    
    public void downloadFile(byte[] binaryContent, String name, String contentType) throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        HttpServletResponse response = (HttpServletResponse) context.getResponse();
        response.setHeader("Content-Disposition", "attachment;filename=\""+name+"\"");
        response.setContentLength(binaryContent.length);
        response.setContentType(contentType + ";charset=UTF-8");

        OutputStream out = response.getOutputStream();
        out.write(binaryContent);
        out.flush();
        out.close();
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public static void redireciona(String url) throws Exception {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect(url);
        } catch (IOException ex) {
            throw new Exception("Erro ao redirecionar para:" + url, ex);
        }
    }
        
}
