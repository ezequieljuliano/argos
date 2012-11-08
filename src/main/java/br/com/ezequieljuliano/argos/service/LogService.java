package br.com.ezequieljuliano.argos.service;

import br.com.ezequieljuliano.argos.business.EventoBC;
import br.com.ezequieljuliano.argos.business.EventoNivelBC;
import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.business.UsuarioBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.exception.LogServiceException;
import br.com.ezequieljuliano.argos.service.to.EventoTO;
import br.com.ezequieljuliano.argos.service.to.LogReturnTO;
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
    @Inject
    private EventoNivelBC eventoNivelBC;
    @Inject
    private EventoTipoBC eventoTipoBC;
    @Inject
    private EventoBC eventoBC;

    @POST
    @Path("/{apiKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    public LogReturnTO log(@PathParam("apiKey") String apiKey, EventoTO evento) {
        try {
            //Valida o usuário se está habilitado para enviar Logs
            //Faz validação baseado na ApiKey
            Entidade entidade = usuarioBC.findEntidadeByApiKey(apiKey);
            if (entidade == null) {
                throw new LogServiceException(LogExceptionTipo.logExUsuarioInvalido);
            }
            //Valida se o nível do log está cadastrado
            EventoNivel eveNivel = eventoNivelBC.findByCodigo(evento.getEventoNivelCodigo());
            if (eveNivel == null) {
                throw new LogServiceException(LogExceptionTipo.logExEventoNivelInvalido);
            }
            //Valida se o tipo do log está cadastrado
            EventoTipo eveTipo = eventoTipoBC.findByCodigo(evento.getEventoTipoCodigo());
            if (eveTipo == null) {
                throw new LogServiceException(LogExceptionTipo.logExEventoTipoInvalido);
            }
            //Insere os logs no sistema
            ArmazenaLogs(evento, entidade, eveNivel, eveTipo);
        } catch (LogServiceException ex) {
            return new LogReturnTO(ex.getLogEx().ordinal(), ex.getMessage());
        }
        //Se tudo ocorrer de forma correta retorna OK
        return new LogReturnTO(0, "Log Gravado com Sucesso!");
    }

    private void ArmazenaLogs(EventoTO log, Entidade entidade, EventoNivel eveNivel, EventoTipo eveTipo) throws LogServiceException {
        try {
            Evento evento = new Evento();
            evento.setHostName(log.getHostName());
            evento.setHostIp(log.getHostIp());
            evento.setHostUser(log.getHostUser());
            evento.setMensagem(log.getMensagem());
            evento.setFonte(log.getFonte());
            evento.setNome(log.getNome());
            evento.setOcorrenciaDtHr(log.getOcorrenciaDtHr());
            evento.setPalavrasChave(log.getPalavrasChave());
            evento.setEntidade(entidade);
            evento.setEventoNivel(eveNivel);
            evento.setEventoTipo(eveTipo);
            eventoBC.saveOrUpdate(evento);
        } catch (Exception ex) {
            throw new LogServiceException(LogExceptionTipo.logExAoInserirLog);
        }
    }
}
