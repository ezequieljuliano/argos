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

import br.com.ezequieljuliano.argos.constant.Constantes;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import java.util.List;
import javax.persistence.Query;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.Filter;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class UsuarioDAO extends GenericDAO<Usuario, String> {

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

    public Usuario findByApiKey(String apiKey) {
        String jpql = "select u from Usuario u where u.apiKey = :apiKey";
        Query qry = createQuery(jpql);
        qry.setParameter("apiKey", apiKey);
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

    @Override
    public Document getLuceneDocument(Usuario obj) {
        Document document = new Document();
        document.add(new Field(Constantes.INDICE_USUARIO_ID, obj.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_USERNAME, obj.getUserName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_PASSWORD, obj.getPassword(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_PERFILCODIGO, obj.getPerfil().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_PERFIL, obj.getPerfil().getNome(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_SITUACAOCODIGO, obj.getSituacao().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_SITUACAO, obj.getSituacao().getNome(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_EMAIL, obj.getEmail(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_APIKEY, obj.getApiKey(), Field.Store.YES, Field.Index.NOT_ANALYZED));

        if (obj.getEntidade() != null) {
            document.add(new Field(Constantes.INDICE_USUARIO_ENTIDADEID, obj.getEntidade().getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
            document.add(new Field(Constantes.INDICE_USUARIO_ENTIDADENOME, obj.getEntidade().getNome(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        }

        document.add(new Field(Constantes.INDICE_USUARIO_TUDO, getLuceneConteudoString(obj), Field.Store.YES, Field.Index.ANALYZED));
        return document;
    }

    @Override
    public String getLuceneIndiceChave() {
        return Constantes.INDICE_USUARIO_ID;
    }

    @Override
    public String getLuceneConteudoString(Usuario obj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getUserName());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getPassword());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getPerfil().toString());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getPerfil().getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getSituacao().toString());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getSituacao().getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEmail());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getApiKey());

        if (obj.getEntidade() != null) {
            stringBuilder.append(" \n ");
            stringBuilder.append(obj.getEntidade().getId());
            stringBuilder.append(" \n ");
            stringBuilder.append(obj.getEntidade().getNome());
        }

        return stringBuilder.toString();
    }

    @Override
    public Filter getLuceneFiltroDeRestricao() {
        return null;
    }
}
