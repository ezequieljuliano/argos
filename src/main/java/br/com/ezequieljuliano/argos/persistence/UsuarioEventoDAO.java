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
import br.com.ezequieljuliano.argos.domain.UsuarioEvento;
import br.com.ezequieljuliano.argos.util.Data;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.Filter;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class UsuarioEventoDAO extends GenericDAO<UsuarioEvento, String> {

    private static final long serialVersionUID = 1L;

    @Override
    public Document getLuceneDocument(UsuarioEvento obj) {
        Document document = new Document();
        document.add(new Field(Constantes.INDICE_USUARIOEVENTO_ID, obj.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIOEVENTO_DATAHORA, Data.timestampToString(obj.getDataHora()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIOEVENTO_EVENTOID, obj.getEvento().getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIOEVENTO_EVENTONOME, obj.getEvento().getNome(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIOEVENTO_USUSARIOID, obj.getUsuario().getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIOEVENTO_USUSARIOUSERNAME, obj.getUsuario().getUserName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIOEVENTO_TUDO, getLuceneConteudoString(obj), Field.Store.YES, Field.Index.ANALYZED));
        return document;
    }

    @Override
    public String getLuceneIndiceChave() {
        return Constantes.INDICE_USUARIOEVENTO_ID;
    }

    @Override
    public String getLuceneConteudoString(UsuarioEvento obj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(Data.timestampToString(obj.getDataHora()));
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEvento().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEvento().getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getUsuario().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getUsuario().getUserName());
        return stringBuilder.toString();
    }

    @Override
    public Filter getLuceneFiltroDeRestricao() {
        return null;
    }
}
