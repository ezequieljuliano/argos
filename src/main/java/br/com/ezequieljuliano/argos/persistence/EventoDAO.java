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
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoPesquisaFiltro;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioPerfil;
import br.com.ezequieljuliano.argos.domain.UsuarioTermoPesquisa;
import br.com.ezequieljuliano.argos.util.Data;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Repository
public class EventoDAO extends GenericLuceneDAO<Evento, String> {

    private static final long serialVersionUID = 1L;
    
    private int numHitsResults = 100;
    
    private Filter luceneFilter = null;
    
    @Autowired
    private EntidadeDAO entidadeDAO;

    public List<Evento> findByPesquisaFiltro(EventoPesquisaFiltro eventoPesquisaFiltro) {
        //Limpa o filtro padrão de restrição caso houver
        this.luceneFilter = null;
        //Seta o número de Hits (Resultados da pesquisa)
        setNumHitsResults(eventoPesquisaFiltro.getNumHitsResults());
        //Filtro por entidade relacionada ao usuário
        BooleanQuery bqFilter = new BooleanQuery();
        //Se for administrador traz todas as entidades
        //Caso contrário adiciona filtro de restrição
        if (!eventoPesquisaFiltro.getUsuario().getPerfil().equals(UsuarioPerfil.administrador)) {
            List<Entidade> entidades = entidadeDAO.findByUsuario(eventoPesquisaFiltro.getUsuario());
            for (Entidade entidade : entidades) {
                bqFilter.add(new TermQuery(new Term(Constantes.INDICE_EVENTO_ENTIDADEID, entidade.getId())), BooleanClause.Occur.SHOULD);
            }
        }
        //Varifica se existem filtros de restrição adicionados
        if (!bqFilter.clauses().isEmpty()) {
            QueryWrapperFilter queryWrapperFilter = new QueryWrapperFilter(bqFilter);
            this.luceneFilter = queryWrapperFilter;
        }
        //Adciona termos da pesquisa (QueryPrincipal AND QueryTermos(TERMO OU TERMO ...) 
        BooleanQuery booleanQuery = new BooleanQuery();
        booleanQuery.add(eventoPesquisaFiltro.getBooleanQuery(), BooleanClause.Occur.MUST);
        return luceneExecQuery(booleanQuery);
    }

    public Boolean possuiTermosDeNotificacao(Evento evento, Usuario usuario) {
        if (!usuario.getTermosNotificacao().isEmpty()) {
            //Cria query principal
            BooleanQuery booleanQuery = new BooleanQuery();
            //Adiciona condição para pegar somente o evento em questão
            booleanQuery.add(new TermQuery(new Term(Constantes.INDICE_EVENTO_ID, evento.getId())), BooleanClause.Occur.MUST);
            //Cria nova query para os termos)
            BooleanQuery bqTerms = new BooleanQuery();
            for (UsuarioTermoPesquisa obj : usuario.getTermosNotificacao()) {
                try {
                    bqTerms.add(new QueryParser(Constantes.getLuceneVersion(), obj.getTermo().getLuceneIndex(), getAnalyzer()).parse(obj.getValor()), BooleanClause.Occur.SHOULD);
                } catch (ParseException ex) {
                    Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //Adciona termos (QueryPrincipal AND QueryTermos(TERMO OU TERMO ...) 
            booleanQuery.add(bqTerms, BooleanClause.Occur.MUST);
            return (!luceneExecQuery(booleanQuery).isEmpty());
        }
        return false;
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
        String newLineCharacter = System.getProperty("line.separator");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(obj.getId()).append(newLineCharacter);
        stringBuilder.append(obj.getHostName()).append(newLineCharacter);
        stringBuilder.append(obj.getHostIp()).append(newLineCharacter);
        stringBuilder.append(obj.getHostUser()).append(newLineCharacter);
        stringBuilder.append(obj.getMensagem()).append(newLineCharacter);
        stringBuilder.append(obj.getFonte()).append(newLineCharacter);
        stringBuilder.append(obj.getNome()).append(newLineCharacter);
        stringBuilder.append(Data.timestampToString(obj.getOcorrenciaDtHr())).append(newLineCharacter);
        stringBuilder.append(obj.getPalavrasChave()).append(newLineCharacter);
        stringBuilder.append(obj.getEntidade().getId()).append(newLineCharacter);
        stringBuilder.append(obj.getEntidade().getCadastroNacional()).append(newLineCharacter);
        stringBuilder.append(obj.getEntidade().getCodigo()).append(newLineCharacter);
        stringBuilder.append(obj.getEntidade().getNome()).append(newLineCharacter);
        stringBuilder.append(obj.getEventoNivel().getId()).append(newLineCharacter);
        stringBuilder.append(obj.getEventoNivel().getCodigo()).append(newLineCharacter);
        stringBuilder.append(obj.getEventoNivel().getDescricao()).append(newLineCharacter);
        stringBuilder.append(obj.getEventoTipo().getId()).append(newLineCharacter);
        stringBuilder.append(obj.getEventoTipo().getCodigo()).append(newLineCharacter);
        stringBuilder.append(obj.getEventoTipo().getDescricao()).append(newLineCharacter);
        return stringBuilder.toString();
    }

    @Override
    public Filter getLuceneFilterRestriction() {
        return this.luceneFilter;
    }

    @Override
    public String getLuceneValueIdKey(Evento obj) {
        return obj.getId();
    }

    @Override
    public int getLuceneNumHits() {
       return getNumHitsResults();
    }

    public int getNumHitsResults() {
        return numHitsResults;
    }

    public void setNumHitsResults(int numHitsResults) {
        this.numHitsResults = numHitsResults;
    }
 
}
