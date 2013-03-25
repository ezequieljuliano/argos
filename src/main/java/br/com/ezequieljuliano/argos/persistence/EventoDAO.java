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
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoPesquisaFiltro;
import br.com.ezequieljuliano.argos.util.Data;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Repository
public class EventoDAO extends GenericLuceneDAO<Evento, String> {

    private static final long serialVersionUID = 1L;
    private Filter luceneFilter = null;

    public List<Evento> findByPesquisaFiltro(EventoPesquisaFiltro filtro) {
        //Reset no filter
        luceneFilter = null;
        //Monta filtro padrão
        List<Term> terms = new ArrayList<Term>();
        //Verifica se o filtro possui entidade
        if (filtro.getEntidade() != null) {
            Term termEntidade = new Term(Constantes.INDICE_EVENTO_ENTIDADEID, filtro.getEntidade().getId());
            terms.add(termEntidade);
        }
        //Verifica se o filtro possui tipo
        if (filtro.getEventoTipo() != null) {
            Term termTipo = new Term(Constantes.INDICE_EVENTO_TIPOID, filtro.getEventoTipo().getId());
            terms.add(termTipo);
        }
        //Verifica se o filtro possui nível
        if (filtro.getEventoNivel() != null) {
            Term termNivel = new Term(Constantes.INDICE_EVENTO_NIVELID, filtro.getEventoNivel().getId());
            terms.add(termNivel);
        }
        if (!terms.isEmpty()) {
            luceneFilter = (Filter) terms;
        }
        return super.luceneParserQuery(filtro.getFiltroTipo().getLuceneIndex(), filtro.getPesquisaValor());
    }

    @Override
    public Document getLuceneDocument(Evento obj) {
        Document document = new Document();
        document.add(new StringField(Constantes.INDICE_EVENTO_ID, obj.getId(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_MENSAGEM, obj.getMensagem(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_HOSTNAME, obj.getHostName(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_HOSTUSER, obj.getHostUser(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_HOSTIP, obj.getHostIp(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_FONTE, obj.getFonte(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_NOME, obj.getNome(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_OCORRENCIADTHR, Data.timestampToString(obj.getOcorrenciaDtHr()), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_PALAVRASCHAVE, obj.getPalavrasChave(), Field.Store.YES));
        document.add(new StringField(Constantes.INDICE_EVENTO_ENTIDADEID, obj.getEntidade().getId(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_ENTIDADENOME, obj.getEntidade().getNome(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_ENTIDADECADASTRONACIONAL, obj.getEntidade().getCadastroNacional(), Field.Store.YES));
        document.add(new StringField(Constantes.INDICE_EVENTO_NIVELID, obj.getEventoNivel().getId(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_NIVELDESCRICAO, obj.getEventoNivel().getDescricao(), Field.Store.YES));
        document.add(new StringField(Constantes.INDICE_EVENTO_TIPOID, obj.getEventoTipo().getId(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_TIPODESCRICAO, obj.getEventoTipo().getDescricao(), Field.Store.YES));
        document.add(new TextField(Constantes.INDICE_EVENTO_TUDO, getLuceneContentString(obj), Field.Store.YES));
        return document;
    }

    @Override
    public String getLuceneIndexKey() {
        return Constantes.INDICE_EVENTO_ID;
    }

    @Override
    public String getLuceneContentString(Evento obj) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getHostName());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getHostIp());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getHostUser());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getMensagem());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getFonte());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(Data.timestampToString(obj.getOcorrenciaDtHr()));
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getPalavrasChave());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEntidade().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEntidade().getCadastroNacional());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEntidade().getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEntidade().getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEventoNivel().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEventoNivel().getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEventoNivel().getDescricao());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEventoTipo().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEventoTipo().getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(obj.getEventoTipo().getDescricao());
        return stringBuilder.toString();
    }

    @Override
    public Filter getLuceneFilterRestriction() {
        return luceneFilter;
    }

    @Override
    public String getValueIdKey(Evento obj) {
        return obj.getId();
    }
}
