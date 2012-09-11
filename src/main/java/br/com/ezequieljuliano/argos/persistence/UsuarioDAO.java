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

import br.com.ezequieljuliano.argos.domain.Situacao;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, String> {

    private static final long serialVersionUID = 1L;

    public Usuario findByUserName(String userName) {
        String jpql = "select u from Usuario u where u.userName = :userName";
        Query qry = createQuery(jpql);
        qry.setParameter("userName", userName);
        List<Usuario> usuarioList = qry.getResultList();
        if (usuarioList == null || usuarioList.isEmpty()) {
            return null;
        }
        return usuarioList.get(0);
    }

    public Usuario findByEmail(String email) {
        String jpql = "select u from Usuario u where u.email = :email";
        Query qry = createQuery(jpql);
        qry.setParameter("email", email);
        List<Usuario> usuarioList = qry.getResultList();
        if (usuarioList == null || usuarioList.isEmpty()) {
            return null;
        }
        return usuarioList.get(0);
    }

    public Usuario login(String userName, String password) {
        String jpql = "select u from Usuario u where u.userName = :userName and u.password = :password";
        Query qry = createQuery(jpql);
        qry.setParameter("userName", userName);
        qry.setParameter("password", password);
        List<Usuario> usuarioList = qry.getResultList();
        if (usuarioList == null || usuarioList.isEmpty() || usuarioList.get(0).isInativo()) {
            return null;
        }
        return usuarioList.get(0);
    }
}
