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
import br.com.ezequieljuliano.argos.domain.EventoPesquisaFiltro;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioEvento;
import br.com.ezequieljuliano.argos.persistence.EventoDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import java.sql.Timestamp;
import java.util.List;
import javax.inject.Inject;
import org.apache.lucene.queryParser.ParseException;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoBC extends DelegateCrud<Evento, String, EventoDAO> {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private EventoDAO dao;
    
    @Inject
    private UsuarioEventoBC usuarioEventoBC;

    public void saveOrUpdate(Evento evento, Usuario usuario) {
        if (evento.getId() == null) {
            insert(evento);
        } else {
            update(evento);
        }
        //Insere Usuário relacionado ao Evento
        gravarUsuarioEvento(evento, usuario);
    }

    private void gravarUsuarioEvento(Evento evento, Usuario usuario) {
        if ((usuario != null) && (evento != null)) {
            UsuarioEvento usuEve = new UsuarioEvento();
            usuEve.setDataHora(new Timestamp(new java.util.Date().getTime()));
            usuEve.setEvento(evento);
            usuEve.setUsuario(usuario);
            usuarioEventoBC.saveOrUpdate(usuEve);
        }
    }

    public List<Evento> findByPesquisaFiltro(EventoPesquisaFiltro filtro) throws ParseException {
        return dao.findByPesquisaFiltro(filtro);
    }
}