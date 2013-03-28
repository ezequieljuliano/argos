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
package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.config.Configuracoes;
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoPesquisaFiltro;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioEvento;
import br.com.ezequieljuliano.argos.persistence.EventoDAO;
import br.com.ezequieljuliano.argos.util.Data;
import br.com.ezequieljuliano.argos.util.EmailSender;
import br.com.ezequieljuliano.argos.util.VelocityTemplate;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.apache.lucene.queryparser.classic.ParseException;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoBC extends GenericBC<Evento, String, EventoDAO> {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private UsuarioEventoBC usuarioEventoBC;

    public void saveOrUpdate(Evento evento, Usuario usuario) {
        if (evento.getId() == null) {
            getDAO().insert(evento);
        } else {
            getDAO().save(evento);
        }
        //Insere Usuário relacionado ao Evento
        gravarUsuarioEvento(evento, usuario);
        //Envia alerta caso esteja configurado para isto
        enviarEmailComAlertaParaUsuario(evento, usuario);
    }

    private void gravarUsuarioEvento(Evento evento, Usuario usuario) {
        if ((usuario != null) && (evento != null)) {
            UsuarioEvento usuarioEvento = new UsuarioEvento();
            usuarioEvento.setDataHora(new Timestamp(new java.util.Date().getTime()));
            usuarioEvento.setEvento(evento);
            usuarioEvento.setUsuario(usuario);
            usuarioEventoBC.saveOrUpdate(usuarioEvento);
        }
    }

    public List<Evento> findByPesquisaFiltro(EventoPesquisaFiltro filtro) throws ParseException {
        return getDAO().findByPesquisaFiltro(filtro);
    }

    private void enviarEmailComAlertaParaUsuario(Evento evento, Usuario usuario) {
        if (evento.getEventoNivel().getAlerta()) {
            try {
                //Monta o HTML para envio de email
                String emailMsg = new VelocityTemplate("email/TemplateEnvioLog.vm")
                        .set("entidade", evento.getEntidade().getCadastroNacional().concat(" - ").concat(evento.getEntidade().getNome()))
                        .set("titulo", evento.getNome())
                        .set("data", Data.dateToString(evento.getOcorrenciaDtHr()))
                        .set("hora", Data.timeToString(evento.getOcorrenciaDtHr()))
                        .set("hostUser", evento.getHostUser())
                        .set("hostIp", evento.getHostIp())
                        .set("hostName", evento.getHostName())
                        .set("fonte", evento.getFonte())
                        .set("eventoTipo", evento.getEventoTipo().getDescricao())
                        .set("eventoNivel", evento.getEventoNivel().getDescricao())
                        .set("palavrasChave", evento.getPalavrasChave())
                        .set("mensagem", evento.getMensagem())
                        .toString();
                //Carrega as configurações e envia o email
                Configuracoes config = Configuracoes.load();
                new EmailSender()
                        .usando(new EmailSender.Servidor()
                        .endereco(config.getEmailServer())
                        .porta(config.getEmailPort())
                        .usuario(config.getEmailUser())
                        .senha(config.getEmailPassword())
                        .ssl(config.getEmailSSL()))
                        .de(config.getEmailUser(), "Sistema de Logging Argos")
                        .para(usuario.getEmail())
                        .comAssunto("Novo Evento de Log no Sistema Argos")
                        .comMensagem(emailMsg)
                        .enviar();
            } catch (Exception ex) {
                Logger.getLogger(EventoBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}