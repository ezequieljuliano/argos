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
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
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
        document.add(new Field(Constantes.TUDO, getConteudoEvento(evento), Store.NO, Index.ANALYZED));
        document.add(new Field(Constantes.INDICE_EVENTO_ID, evento.getId(), Store.YES, Index.NOT_ANALYZED_NO_NORMS, TermVector.WITH_POSITIONS_OFFSETS));
        document.add(new Field(Constantes.INDICE_COMPUTADOR_GERADOR, evento.getComputadorGerador(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_FONTE, evento.getFonte(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_NOME, evento.getNome(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_OCORRENCIA_DATA, Data.dateToStr(evento.getOcorrenciaData()), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_OCORRENCIA_HORA, Data.dateToStr(evento.getOcorrenciaHora()), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_PALAVRAS_CHAVE, evento.getPalavrasChave(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_USUARIO_GERADOR, evento.getUsuarioGerador(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_ENTIDADE_ID, evento.getEntidade().getId(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_EVENTO_NIVEL_ID, evento.getEventoNivel().getId(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
        document.add(new Field(Constantes.INDICE_EVENTO_TIPO_ID, evento.getEventoTipo().getId(), Store.YES, Index.NOT_ANALYZED_NO_NORMS));
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
        stringBuilder.append(Data.dateToStr(evento.getOcorrenciaData()));
        stringBuilder.append(" \n ");
        stringBuilder.append(Data.timeToStr(evento.getOcorrenciaHora()));
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
        IndexWriter indexWriter = null;
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

    public List<Evento> findByTudo(String campoPesquisa) {
        Query query = new TermQuery(new Term(Constantes.TUDO, campoPesquisa));
        return executeQuery(query, Constantes.TUDO);
    }

    private List<Evento> executeQuery(Query q, String field) {

        List<Evento> eventos = new ArrayList<Evento>();

        try {
            int hitsPerPage = 30;

            IndexReader reader = IndexReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs topDocs = searcher.search(q, hitsPerPage);
            ScoreDoc[] hits = topDocs.scoreDocs;

            for (int i = 0; i < hits.length; ++i) {

                int docId = hits[i].doc;

                Document d = searcher.doc(docId);
                String id = d.get(Constantes.INDICE_EVENTO_ID);
                Evento evento = eventoDAO.load(id);
                if (evento != null) {
                    eventos.add(evento);
                }
            }
        } catch (Exception exception) {
            Logger.getLogger(EventoLuceneDAO.class.getName()).log(Level.SEVERE,
                    null, exception);
        }
        return eventos;
    }
}