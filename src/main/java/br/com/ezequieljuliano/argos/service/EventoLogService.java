/*
 * Copyright 2012 Ezequiel Juliano Müller.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.ezequieljuliano.argos.service;

import br.com.ezequieljuliano.argos.business.EventoBC;
import br.com.ezequieljuliano.argos.business.EventoNivelBC;
import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.business.UsuarioBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.exception.LogServiceException;
import br.com.ezequieljuliano.argos.service.to.EventoLogReturnTO;
import br.com.ezequieljuliano.argos.service.to.EventoLogTO;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/eventolog")
@Stateless
public class EventoLogService {

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
    @Produces(MediaType.APPLICATION_JSON)
    public EventoLogReturnTO efetuarLogging(@PathParam("apiKey") String apiKey, EventoLogTO evento) {
        try {
            //Pega o usuário
            Usuario usuario = getUsuarioByApiKey(apiKey);
            //Pega a Entidade
            Entidade entidade = getEntidadeByUsuario(usuario);
            //Pega o Evento Nível
            EventoNivel eventoNivel = getEventoNivelByCodigo(evento.getEventoNivelCodigo());
            //Pega Evento Tipo
            EventoTipo eventoTipo = getEventoTipoByCodigo(evento.getEventoTipoCodigo());
            //Insere os logs no sistema
            armazenarEventoLog(evento, entidade, eventoNivel, eventoTipo, usuario);
        } catch (LogServiceException ex) {
            return new EventoLogReturnTO(ex.getLogEx().getCodigo(), ex.getLogEx().getDescricao());
        }
        //Se tudo ocorrer de forma correta retorna OK
        return new EventoLogReturnTO(0, "Logging Efetuado com Sucesso!");
    }

    private void armazenarEventoLog(EventoLogTO log, Entidade entidade, EventoNivel eveNivel, EventoTipo eveTipo, Usuario usuario) throws LogServiceException {
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
            eventoBC.saveOrUpdate(evento, usuario);
        } catch (Exception ex) {
            throw new LogServiceException(EventoLogExceptionTipo.logExAoInserirLog);
        }
    }

    private Usuario getUsuarioByApiKey(String apiKey) throws LogServiceException {
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
        return usuario;
    }

    private Entidade getEntidadeByUsuario(Usuario usuario) throws LogServiceException {
        //Verifica se o usuário possui entidade vinculada
        Entidade entidade = usuario.getEntidade();
        if (entidade == null) {
            throw new LogServiceException(EventoLogExceptionTipo.logExUsuarioSemEntidade);
        }
        //Verifica se a entidade não está inativa
        if (entidade.isInativo()) {
            throw new LogServiceException(EventoLogExceptionTipo.logExEntidadeInativa);
        }
        return entidade;
    }

    private EventoNivel getEventoNivelByCodigo(int codigo) throws LogServiceException {
        //Valida se o nível do log está cadastrado
        EventoNivel eventoNivel = eventoNivelBC.findByCodigo(codigo);
        if (eventoNivel == null) {
            throw new LogServiceException(EventoLogExceptionTipo.logExEventoNivelInvalido);
        }
        if (eventoNivel.isInativo()) {
            throw new LogServiceException(EventoLogExceptionTipo.logExEventoNivelInativo);
        }
        return eventoNivel;
    }

    private EventoTipo getEventoTipoByCodigo(int codigo) throws LogServiceException {
        //Valida se o tipo do log está cadastrado
        EventoTipo eventoTipo = eventoTipoBC.findByCodigo(codigo);
        if (eventoTipo == null) {
            throw new LogServiceException(EventoLogExceptionTipo.logExEventoTipoInvalido);
        }
        if (eventoTipo.isInativo()) {
            throw new LogServiceException(EventoLogExceptionTipo.logExEventoTipoInativo);
        }
        return eventoTipo;
    }
}
