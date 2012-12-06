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
import br.com.ezequieljuliano.argos.domain.EventoTipo;
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
public class EventoTipoDAO extends GenericDAO<EventoTipo, String> {

    private static final long serialVersionUID = 1L;

    public EventoTipo findByCodigo(int codigo) {
        String jpql = "select e from EventoTipo e where e.codigo = :codigo";
        Query qry = createQuery(jpql);
        qry.setParameter("codigo", codigo);
        List<EventoTipo> eventoTipoList = qry.getResultList();
        if (eventoTipoList == null || eventoTipoList.isEmpty()) {
            return null;
        }
        return eventoTipoList.get(0);
    }

    @Override
    public Document getLuceneDocument(EventoTipo obj) {
        Document document = new Document();
        document.add(new Field(Constantes.INDICE_EVENTOTIPO_ID, obj.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOTIPO_CODIGO, Integer.toString(obj.getCodigo()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOTIPO_DESCRICAO, obj.getDescricao(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOTIPO_SITUACAOCODIGO, obj.getSituacao().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOTIPO_SITUACAO, obj.getSituacao().getNome(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOTIPO_TUDO, getLuceneConteudoString(obj), Field.Store.YES, Field.Index.ANALYZED));
        return document;
    }

    @Override
    public String getLuceneIndiceChave() {
        return Constantes.INDICE_EVENTOTIPO_ID;
    }

    @Override
    public String getLuceneConteudoString(EventoTipo obj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getDescricao());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getSituacao().toString());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getSituacao().getNome());
        return stringBuilder.toString();
    }

    @Override
    public Filter getLuceneFiltroDeRestricao() {
        return null;
    }
}
