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
import br.com.ezequieljuliano.argos.persistence.EventoLuceneDAO;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import org.apache.lucene.queryParser.ParseException;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@BusinessController
public class EventoLuceneBC implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    EventoLuceneDAO eventoLuceneDAO;

    public List<Evento> findByTudo(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByTudo(campoPesquisa);
    }

    public List<Evento> findByComputadorGerador(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByComputadorGerador(campoPesquisa);
    }

    public List<Evento> findByFonte(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByFonte(campoPesquisa);
    }

    public List<Evento> findByNome(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByNome(campoPesquisa);
    }

    public List<Evento> findByOcorrenciaData(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByOcorrenciaData(campoPesquisa);
    }

    public List<Evento> findByPalavrasChave(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByPalavrasChave(campoPesquisa);
    }

    public List<Evento> findByUsuarioGerador(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByUsuarioGerador(campoPesquisa);
    }

    public List<Evento> findByEntidadeNome(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByEntidadeNome(campoPesquisa);
    }

    public List<Evento> findByEventoNivelDescricao(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByEventoNivelDescricao(campoPesquisa);
    }

    public List<Evento> findByEventoTipoDescricao(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByEventoTipoDescricao(campoPesquisa);
    }

    public List<Evento> findByEventoDescricao(String campoPesquisa) throws ParseException {
        return eventoLuceneDAO.findByEventoDescricao(campoPesquisa);
    }
}
