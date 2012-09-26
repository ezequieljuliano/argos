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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@RequestScoped
public class EventoLuceneDAO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Analyzer analyzer;
    @Inject
    private Directory directory;
    @Inject
    private EventoDAO eventoDAO;

    @Inject
    public void init() {
        analyzer = new StandardAnalyzer(Constantes.getVersion());
    }

    private Document criarDocumento(Evento evento) throws IOException {
        Document document = new Document();
        document.add(new Field(Constantes.INDICE_EVENTO_ID, evento.getId(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTO_DESCRICAO, evento.getDescricao(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_COMPUTADOR_GERADOR, evento.getComputadorGerador(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_FONTE, evento.getFonte(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_NOME, evento.getNome(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_OCORRENCIA_DATA, Data.dateToString(evento.getOcorrenciaData()), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_OCORRENCIA_HORA, Data.timeToString(evento.getOcorrenciaHora()), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_PALAVRAS_CHAVE, evento.getPalavrasChave(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_USUARIO_GERADOR, evento.getUsuarioGerador(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_ID, evento.getEntidade().getId(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_ENTIDADE_NOME, evento.getEntidade().getNome(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTO_NIVEL_ID, evento.getEventoNivel().getId(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTO_NIVEL_DESCRICAO, evento.getEventoNivel().getDescricao(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTO_TIPO_ID, evento.getEventoTipo().getId(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTO_TIPO_DESCRICAO, evento.getEventoTipo().getDescricao(), Store.YES, Index.ANALYZED));
        document.add(new Field(Constantes.TUDO, getConteudoEvento(evento), Store.YES, Index.ANALYZED));
        return document;
    }

    private String getConteudoEvento(Evento evento) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(evento.getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getDescricao());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getComputadorGerador());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEnderecoIp());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getFonte());
        stringBuilder.append(" \n ");
        stringBuilder.append(Data.dateToString(evento.getOcorrenciaData()));
        stringBuilder.append(" \n ");
        stringBuilder.append(Data.timeToString(evento.getOcorrenciaHora()));
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getPalavrasChave());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getUsuarioGerador());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEntidade().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEntidade().getCadastroNacional());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEntidade().getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEntidade().getNome());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEventoNivel().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEventoNivel().getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEventoNivel().getDescricao());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEventoTipo().getId());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEventoTipo().getCodigo());
        stringBuilder.append(" \n ");
        stringBuilder.append(evento.getEventoTipo().getDescricao());
        return stringBuilder.toString();
    }

    public void salvar(Evento evento) {
        IndexWriterConfig indexConfig = new IndexWriterConfig(Constantes.getVersion(), analyzer);
        IndexWriter indexWriter;
        try {
            indexWriter = new IndexWriter(directory, indexConfig);
            indexWriter.addDocument(criarDocumento(evento));
            indexWriter.commit();
            indexWriter.close();
        } catch (Exception exception) {
            Logger.getLogger(EventoLuceneDAO.class.getName()).log(Level.SEVERE,
                    null, exception);
        }
    }

    public List<Evento> findByFiltro(EventoPesquisaFiltro filtro) throws ParseException {
        Query query = new QueryParser(Constantes.getVersion(), filtro.getFiltroTipo().getLuceneIndex(), analyzer).parse(filtro.getPesquisaValor());
        return executeQuery(query, filtro);
    }

    private List<Evento> executeQuery(Query q, EventoPesquisaFiltro filtro) {
        try {
            int hitsPerPage = 30;
            IndexReader reader = IndexReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;

            List<Evento> eventosList = new ArrayList<Evento>();
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                if (satisfazFiltro(filtro, d)) {
                    String id = d.get(Constantes.INDICE_EVENTO_ID);
                    eventosList.add(eventoDAO.load(id));
                }
            }
            return eventosList;
        } catch (Exception exception) {
            Logger.getLogger(EventoLuceneDAO.class.getName()).log(Level.SEVERE,
                    null, exception);
        }
        return new ArrayList<Evento>();
    }

    private boolean satisfazFiltro(EventoPesquisaFiltro filtro, Document d) {
        boolean satisfazEntidade = (filtro.getEntidade() == null) || (filtro.getEntidade().getId().equals(d.get(Constantes.INDICE_ENTIDADE_ID)));
        boolean satisfazNivel = (filtro.getEventoNivel() == null) || (filtro.getEventoNivel().getId().equals(d.get(Constantes.INDICE_EVENTO_NIVEL_ID)));
        boolean satisfazTipo = (filtro.getEventoTipo() == null) || (filtro.getEventoTipo().getId().equals(d.get(Constantes.INDICE_EVENTO_TIPO_ID)));
        return satisfazEntidade && satisfazNivel && satisfazTipo;
    }
}