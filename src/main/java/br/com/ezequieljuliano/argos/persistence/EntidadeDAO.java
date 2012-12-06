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
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import java.util.List;
import javax.persistence.Query;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.search.Filter;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@PersistenceController
public class EntidadeDAO extends GenericDAO<Entidade, String> {

    private static final long serialVersionUID = 1L;

    public Entidade findByCodigo(int codigo) {
        String jpql = "select e from Entidade e where e.codigo = :codigo";
        Query qry = createQuery(jpql);
        qry.setParameter("codigo", codigo);
        List<Entidade> entidadeList = qry.getResultList();
        if (entidadeList == null || entidadeList.isEmpty()) {
            return null;
        }
        return entidadeList.get(0);
    }

    public Entidade findByCadastroNacional(String cadastroNacional) {
        String jpql = "select e from Entidade e where e.cadastroNacional = :cadastroNacional";
        Query qry = createQuery(jpql);
        qry.setParameter("cadastroNacional", cadastroNacional);
        List<Entidade> entidadeList = qry.getResultList();
        if (entidadeList == null || entidadeList.isEmpty()) {
            return null;
        }
        return entidadeList.get(0);
    }

    @Override
    public Document getLuceneDocument(Entidade obj) {
        Document document = new Document();
        document.add(new Field(Constantes.INDICE_ENTIDADE_ID, obj.getId(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_CODIGO, Integer.toString(obj.getCodigo()), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_CADASTRONACIONAL, obj.getCadastroNacional(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_NOME, obj.getNome(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_SITUACAOCODIGO, obj.getSituacao().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_SITUACAO, obj.getSituacao().getNome(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_TUDO, getLuceneConteudoString(obj), Store.YES, Index.ANALYZED));
        return document;
    }

    @Override
    public String getLuceneIndiceChave() {
        return Constantes.INDICE_ENTIDADE_ID;
    }

    @Override
    public String getLuceneConteudoString(Entidade obj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getCadastroNacional());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getNome());
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
