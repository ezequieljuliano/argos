package br.com.ezequieljuliano.argos.service;

import br.com.ezequieljuliano.argos.business.EventoBC;
import br.com.ezequieljuliano.argos.business.EventoCampoCustomizadoBC;
import br.com.ezequieljuliano.argos.business.EventoNivelBC;
import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.business.UsuarioBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoCampoCustomizado;
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.domain.EventoValorCustomizado;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.exception.LogServiceException;
import br.com.ezequieljuliano.argos.service.to.EventoLogReturnTO;
import br.com.ezequieljuliano.argos.service.to.EventoLogTO;
import br.com.ezequieljuliano.argos.service.to.EventoValorCustomizadoTO;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/eventolog")
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class EventoLogService {

    @Inject
    private UsuarioBC usuarioBC;
    
    @Inject
    private EventoNivelBC eventoNivelBC;
    
    @Inject
    private EventoTipoBC eventoTipoBC;
    
    @Inject
    private EventoBC eventoBC;
    
    @Inject
    private EventoCampoCustomizadoBC eventoCampoCustomizadoBC;

    @POST
    @Path("/{apiKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public EventoLogReturnTO efetuarLogging(@PathParam("apiKey") String apiKey, EventoLogTO evento) {
        try {
            //Valida o usuário se está habilitado para enviar Logs
            //Busca o usuário pela ApiKey
            Usuario usuario = usuarioBC.findByApiKey(apiKey);
            if (usuario == null) {
                throw new LogServiceException(EventoLogExceptionTipo.logExUsuarioInvalido);
            }
            //Verifica se o usuário não está inativo
            if (usuario.isInativo()) {
                throw new LogServiceException(EventoLogExceptionTipo.logExUsuarioInativo);
            }
            //Verifica se o usuário possui entidade vinculada
            Entidade entidade = usuario.getEntidade();
            if (entidade == null) {
                throw new LogServiceException(EventoLogExceptionTipo.logExUsuarioSemEntidade);
            }
            //Verifica se a entidade não está inativa
            if (entidade.isInativo()){
               throw new LogServiceException(EventoLogExceptionTipo.logExEntidadeInativa); 
            }
            //Valida se o nível do log está cadastrado
            EventoNivel eveNivel = eventoNivelBC.findByCodigo(evento.getEventoNivelCodigo());
            if (eveNivel == null) {
                throw new LogServiceException(EventoLogExceptionTipo.logExEventoNivelInvalido);
            }
            if (eveNivel.isInativo()){
                throw new LogServiceException(EventoLogExceptionTipo.logExEventoNivelInativo);
            }
            //Valida se o tipo do log está cadastrado
            EventoTipo eveTipo = eventoTipoBC.findByCodigo(evento.getEventoTipoCodigo());
            if (eveTipo == null) {
                throw new LogServiceException(EventoLogExceptionTipo.logExEventoTipoInvalido);
            }
            if (eveTipo.isInativo()){
                throw new LogServiceException(EventoLogExceptionTipo.logExEventoTipoInativo);
            }
            //Insere os logs no sistema
            armazenarLogs(evento, entidade, eveNivel, eveTipo, usuario);
        } catch (LogServiceException ex) {
            return new EventoLogReturnTO(ex.getLogEx().getCodigo(), ex.getLogEx().getDescricao());
        }
        //Se tudo ocorrer de forma correta retorna OK
        return new EventoLogReturnTO(0, "Logging Efetuado com Sucesso!");
    }

    private void armazenarLogs(EventoLogTO log, Entidade entidade, EventoNivel eveNivel, EventoTipo eveTipo, Usuario usuario) throws LogServiceException {
        try {
            Evento evento = new Evento();
            evento.setHostName(log.getHostName());
            evento.setHostIp(log.getHostIp());
            evento.setHostUser(log.getHostUser());
            evento.setMensagem(log.getMensagem());
            evento.setFonte(log.getFonte());
            evento.setNome(log.getNome());
            evento.setOcorrenciaDtHr(log.getOcorrenciaDtHrAsDate());
            evento.setPalavrasChave(log.getPalavrasChave());
            evento.setEntidade(entidade);
            evento.setEventoNivel(eveNivel);
            evento.setEventoTipo(eveTipo);
            //Percorre e Insere os valores personalizados
            for (EventoValorCustomizadoTO eveVlCustomTO : log.getValoresCustomizados()) {
                //Deve possuir campo e valor
                if (!eveVlCustomTO.getCampo().equals("") && (!eveVlCustomTO.getValor().equals(""))) {
                    //Verifica se o campo já está inserido e vinculado a entidade em questão
                    EventoCampoCustomizado eveCampoCustom = eventoCampoCustomizadoBC.findByDescricaoAndEntidade(eveVlCustomTO.getCampo(), entidade);
                    if (eveCampoCustom == null){
                        //Insere o campo vinculado a entidade
                        eveCampoCustom = new EventoCampoCustomizado();
                        eveCampoCustom.setDescricao(eveVlCustomTO.getCampo());
                        eveCampoCustom.setEntidade(entidade);
                        eventoCampoCustomizadoBC.saveOrUpdate(eveCampoCustom);
                    }
                    //Agora insere os valores personalizados vinculados a aquele campo
                    EventoValorCustomizado eveVlCustom = new EventoValorCustomizado();
                    eveVlCustom.setValor(eveVlCustomTO.getValor());
                    eveVlCustom.setEventoCampoCustomizado(eveCampoCustom);
                    //Adiciona ao evento
                    evento.addValorCustomizado(eveVlCustom);
                }
            }
            //Persiste o evento LOG
            eventoBC.saveOrUpdate(evento, usuario);
        } catch (Exception ex) {
            throw new LogServiceException(EventoLogExceptionTipo.logExAoInserirLog);
        }
    }
}
