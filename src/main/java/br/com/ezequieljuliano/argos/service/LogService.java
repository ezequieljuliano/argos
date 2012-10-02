package br.com.ezequieljuliano.argos.service;

import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.persistence.EventoDAO;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/log")
@Stateless
public class LogService {
    
//    @Inject
//    private EventoDAO eventoDAO;
//    
//    @GET
//    @Path("/list")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Evento> list(){
//        return eventoDAO.findAll();
//    }
//    
//    @GET
//    @Path("/get")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Evento get(){
//        return eventoDAO.findAll().get(0);
//    }
}
