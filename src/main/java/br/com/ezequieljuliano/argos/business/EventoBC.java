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

import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.persistence.EventoDAO;
import br.com.ezequieljuliano.argos.util.Utils;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import java.sql.Timestamp;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoBC extends DelegateCrud<Evento, String, EventoDAO> {

    private static final long serialVersionUID = 1L;
    @Inject
    private EntidadeBC entidadeBC;
    @Inject
    private EventoNivelBC eventoNivelBC;
    @Inject
    private EventoTipoBC eventoTipoBC;

    public void saveOrUpdate(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(Utils.getUniqueId());
            insert(evento);
        } else {
            update(evento);
        }
    }

//    @Startup
//    @Transactional
//    public void insereEventos() {
//        Evento evento = new Evento();
//        evento.setHostName("EZEQUIEL-NB");
//        evento.setMensagem("Login no sistema Msys");
//        evento.setHostIp("192.168.0.10");
//        evento.setFonte("Microsys");
//        evento.setNome("Login no Msys");
//        evento.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
//        evento.setPalavrasChave("LOGIN;MSYS;SISTEMA");
//        evento.setHostUser("Ezequiel");
//        evento.setEntidade(entidadeBC.findByCodigo(2));
//        evento.setEventoNivel(eventoNivelBC.findByCodigo(1));
//        evento.setEventoTipo(eventoTipoBC.findByCodigo(1));
//        saveOrUpdate(evento);
//
//        Evento evento2 = new Evento();
//        evento2.setHostName("LUIZ-NB");
//        evento2.setMensagem("Alteração de dados do cliente 50");
//        evento2.setHostIp("192.168.0.50");
//        evento2.setFonte("Cadastro de Clientes");
//        evento2.setNome("Alteração de Clientes");
//        evento2.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
//        evento2.setPalavrasChave("ALTERAÇÃO;CLIENTE;SISTEMA");
//        evento2.setHostUser("Luiz");
//        evento2.setEntidade(entidadeBC.findByCodigo(1));
//        evento2.setEventoNivel(eventoNivelBC.findByCodigo(2));
//        evento2.setEventoTipo(eventoTipoBC.findByCodigo(2));
//        saveOrUpdate(evento2);
//
//        Evento evento3 = new Evento();
//        evento3.setHostName("EVANDRO-NB");
//        evento3.setMensagem("Exclusão de cliente 120");
//        evento3.setHostIp("192.168.0.38");
//        evento3.setFonte("Cadastro de Clientes");
//        evento3.setNome("Exclusão de Clientes");
//        evento3.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
//        evento3.setPalavrasChave("EXCLUSÃO;CLIENTE;SISTEMA");
//        evento3.setHostUser("Evandro");
//        evento3.setEntidade(entidadeBC.findByCodigo(1));
//        evento3.setEventoNivel(eventoNivelBC.findByCodigo(3));
//        evento3.setEventoTipo(eventoTipoBC.findByCodigo(3));
//        saveOrUpdate(evento3);
//
//        Evento evento4 = new Evento();
//        evento4.setHostName("LUCIANO-PC");
//        evento4.setMensagem("Exclusão de pedido de venda 600");
//        evento4.setHostIp("192.168.0.44");
//        evento4.setFonte("Pedidos de Vendas");
//        evento4.setNome("Exclusão de Pedidos de Vendas");
//        evento4.setOcorrenciaDtHr(new Timestamp(new java.util.Date().getTime()));
//        evento4.setPalavrasChave("EXCLUSÃO;PEDIDO DE VENDA;SISTEMA");
//        evento4.setHostUser("Luciano");
//        evento4.setEntidade(entidadeBC.findByCodigo(1));
//        evento4.setEventoNivel(eventoNivelBC.findByCodigo(4));
//        evento4.setEventoTipo(eventoTipoBC.findByCodigo(4));
//        saveOrUpdate(evento4);
//
//    }
}