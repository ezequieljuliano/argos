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
import java.sql.Date;
import java.sql.Time;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoBC extends DelegateCrud<Evento, String, EventoDAO> {

    private static final long serialVersionUID = 1L;
    @Inject
    EntidadeBC entidadeBC;
    @Inject
    EventoNivelBC eventoNivelBC;
    @Inject
    EventoTipoBC eventoTipoBC;

    public void saveOrUpdate(Evento evento) {
        if (evento.getId() == null) {
            evento.setId(Utils.getUniqueId());
            insert(evento);
        } else {
            update(evento);
        }
    }

    @Startup
    @Transactional
    public void insereEventos() {
        Evento evento = new Evento();
        evento.setComputadorGerador("EZEQUIEL-NB");
        evento.setDescricao("Login no sistema Argos");
        evento.setEnderecoIp("192.168.0.10");
        evento.setFonte("ARGOS");
        evento.setNome("Login Argos");
        evento.setOcorrenciaData(new Date(serialVersionUID));
        evento.setOcorrenciaHora(new Time(serialVersionUID));
        evento.setPalavrasChave("LOGIN;ARGOS;SISTEMA");
        evento.setUsuarioGerador("ADM");
        evento.setEntidade(entidadeBC.findByCodigo(1));
        evento.setEventoNivel(eventoNivelBC.findByCodigo(1));
        evento.setEventoTipo(eventoTipoBC.findByCodigo(1));
        saveOrUpdate(evento);

        Evento evento2 = new Evento();
        evento2.setComputadorGerador("LUIZ-NB");
        evento2.setDescricao("Alteração de Dados no sistema Argos");
        evento2.setEnderecoIp("192.168.0.10");
        evento2.setFonte("PEDIDOS");
        evento2.setNome("Alteração Argos");
        evento2.setOcorrenciaData(new Date(serialVersionUID));
        evento2.setOcorrenciaHora(new Time(serialVersionUID));
        evento2.setPalavrasChave("ALTERACAO;ARGOS;SISTEMA");
        evento2.setUsuarioGerador("ADM");
        evento2.setEntidade(entidadeBC.findByCodigo(1));
        evento2.setEventoNivel(eventoNivelBC.findByCodigo(2));
        evento2.setEventoTipo(eventoTipoBC.findByCodigo(2));
        saveOrUpdate(evento2);
    }
}
