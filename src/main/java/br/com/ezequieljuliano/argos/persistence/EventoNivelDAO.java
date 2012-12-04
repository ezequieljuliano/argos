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
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import java.util.List;
import javax.persistence.Query;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class EventoNivelDAO extends GenericDAO<EventoNivel, String> {

    private static final long serialVersionUID = 1L;

    public EventoNivel findByCodigo(int codigo) {
        String jpql = "select e from EventoNivel e where e.codigo = :codigo";
        Query qry = createQuery(jpql);
        qry.setParameter("codigo", codigo);
        List<EventoNivel> eventoNivelList = qry.getResultList();
        if (eventoNivelList == null || eventoNivelList.isEmpty()) {
            return null;
        }
        return eventoNivelList.get(0);
    }

    @Override
    public Document getLuceneDocument(EventoNivel obj) {
        Document document = new Document();
        document.add(new Field(Constantes.INDICE_EVENTONIVEL_ID, obj.getId(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTONIVEL_CODIGO, Integer.toString(obj.getCodigo()), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTONIVEL_DESCRICAO, obj.getDescricao(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTONIVEL_SITUACAO, obj.getSituacao().getNome(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTONIVEL_ALERTA, Boolean.toString(obj.getAlerta()), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTONIVEL_TUDO, getLuceneConteudoString(obj), Store.YES, Index.ANALYZED));
        return document;
    }

    @Override
    public String getLuceneIndiceChave() {
        return Constantes.INDICE_EVENTONIVEL_ID;
    }

    @Override
    public String getLuceneConteudoString(EventoNivel obj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getDescricao());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getSituacao().getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(Boolean.toString(obj.getAlerta()));
        return stringBuilder.toString();
    }
}
