package br.com.ezequieljuliano.argos.service;

import br.com.ezequieljuliano.argos.business.UsuarioBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.exception.AutenticationException;
import br.com.ezequieljuliano.argos.service.to.EventoTO;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/log")
@Stateless
public class LogService {

    @Inject
    private UsuarioBC usuarioBC;

    @POST
    @Path("/{apiKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Integer log(@PathParam("apiKey") String apiKey, EventoTO evento) {
        try {
            Entidade entidade = usuarioBC.findEntidadeByApiKey(apiKey);
            if (entidade == null) {
                throw new AutenticationException("Usuário inválido!");
            }
            
        } catch (AutenticationException ex) {
            return 1;
        } 

        return 0;
    }
}
