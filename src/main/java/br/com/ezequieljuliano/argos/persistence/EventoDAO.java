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
package br.com.ezequieljuliano.argos.persistence;

import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioPerfil;
import br.com.ezequieljuliano.argos.security.SessionAttributes;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.Query;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class EventoDAO extends BaseDAO<Evento, String> {

    private static final long serialVersionUID = 1L;
    @Inject
    EventoLuceneDAO eventoLuceneDAO;
    @Inject
    private SessionAttributes sessionAttributes;

    @Override
    public void insert(Evento entity) {
        eventoLuceneDAO.salvar(entity);
        super.insert(entity);
    }

    @Override
    public void update(Evento entity) {
        eventoLuceneDAO.salvar(entity);
        super.update(entity);
    }

    @Override
    public Evento load(String id) {
        String jpql;
        Query query;

        Usuario usuarioLogado = sessionAttributes.getUsuario();
        UsuarioPerfil perfil = usuarioLogado.getPerfil();
        if (!perfil.equals(UsuarioPerfil.administrador)) {
            jpql = "select e from Evento e where (e.id = :id) and (e.entidade.id = :entidadeId)";
            query = createQuery(jpql);
            query.setParameter("id", id);
            query.setParameter("entidadeId", usuarioLogado.getEntidade().getId());
        } else {
            jpql = "select e from Evento e where (e.id = :id)";
            query = createQuery(jpql);
            query.setParameter("id", id);
        }

        List<Evento> eventoList = query.getResultList();
        if (eventoList == null || eventoList.isEmpty()) {
            return null;
        }
        return eventoList.get(0);
    }

    @Override
    public List<Evento> findAll() {
        String jpql;
        Query query;

        Usuario usuarioLogado = sessionAttributes.getUsuario();
        UsuarioPerfil perfil = usuarioLogado.getPerfil();
        if (!perfil.equals(UsuarioPerfil.administrador)) {
            jpql = "select e from Evento e where e.entidade.id = :id";
            query = createQuery(jpql);
            query.setParameter("id", usuarioLogado.getEntidade().getId());
        } else {
            jpql = "select e from Evento e";
            query = createQuery(jpql);
        }

        return query.getResultList();
    }
}
