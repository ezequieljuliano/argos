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
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.EventoCampoCustomizado;
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.TermsFilter;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class EventoCampoCustomizadoDAO extends GenericDAO<EventoCampoCustomizado, String> {

    private static final long serialVersionUID = 1L;
    private Filter luceneFilter = null;

    public EventoCampoCustomizado findByDescricaoAndEntidade(String descricao, Entidade entidade) {
        //Adiciona o filtro por entidade
        Term term = new Term(Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_ENTIDADEID, entidade.getId());
        TermsFilter termFilter = new TermsFilter();
        termFilter.addTerm(term);
        luceneFilter = termFilter;
        //Busca por descrição respeitando o filtro
        List<EventoCampoCustomizado> objList = super.luceneTermQuery(Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_DESCRICAO, descricao);
        if (objList == null || objList.isEmpty()) {
            return null;
        }
        return objList.get(0);
    }

    @Override
    public Document getLuceneDocument(EventoCampoCustomizado obj) {
        Document document = new Document();
        document.add(new Field(Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_ID, obj.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_DESCRICAO, obj.getDescricao(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_ENTIDADEID, obj.getEntidade().getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_ENTIDADENOME, obj.getEntidade().getNome(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_TUDO, getLuceneConteudoString(obj), Field.Store.YES, Field.Index.ANALYZED));
        return document;
    }

    @Override
    public String getLuceneIndiceChave() {
        return Constantes.INDICE_EVENTOCAMPOCUSTOMIZADO_ID;
    }

    @Override
    public String getLuceneConteudoString(EventoCampoCustomizado obj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getDescricao());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEntidade().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEntidade().getNome());
        return stringBuilder.toString();
    }

    @Override
    public Filter getLuceneFiltroDeRestricao() {
        return luceneFilter;
    }
}
