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
import br.gov.frameworkdemoiselle.template.JPACrud;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public abstract class GenericDAO<DomainType, KeyType> extends JPACrud<DomainType, KeyType> {

    private static final long serialVersionUID = 1L;
    
    private Analyzer analyzer;
    
    @Inject
    private Directory directory;

    @Inject
    public void init() {
        analyzer = new StandardAnalyzer(Constantes.getLuceneVersion());
    }

    //Métodos que devem ser escritos nas classes extendidas
    public abstract Document getLuceneDocument(DomainType obj);
    public abstract String getLuceneIndiceChave();
    public abstract String getLuceneConteudoString(DomainType obj);

    private IndexWriter getIndexWriter() {
        try {
            IndexWriterConfig indexConfig = new IndexWriterConfig(Constantes.getLuceneVersion(), analyzer);
            return new IndexWriter(directory, indexConfig);
        } catch (CorruptIndexException ex) {
            Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (LockObtainFailedException ex) {
            Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void luceneSalvar(DomainType obj) {
        Document doc = getLuceneDocument(obj);
        if (doc != null) {
            IndexWriter indexWriter;
            try {
                indexWriter = getIndexWriter();
                indexWriter.addDocument(doc);
                indexWriter.commit();
                indexWriter.close();
            } catch (Exception exception) {
                Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE,
                        null, exception);
            }
        }
    }
    
    private void luceneExcluir(KeyType id) {
        IndexWriter indexWriter;
        try {
            //Pega o valor da chave e converter para string
            String idTerm = (String) id;
            indexWriter = getIndexWriter();
            indexWriter.deleteDocuments(new Term(getLuceneIndiceChave(), idTerm));
            indexWriter.commit();
            indexWriter.close();
        } catch (Exception exception) {
            Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE,
                    null, exception);
        }
    }
    
    private void luceneAtualizar(DomainType obj) {
        Document doc = getLuceneDocument(obj);
        if (doc != null) {
            IndexWriter indexWriter;
            try {
                //Pega o valor da chave e converter para string
                String idTerm = doc.get(getLuceneIndiceChave());
                indexWriter = getIndexWriter();
                indexWriter.updateDocument(new Term(getLuceneIndiceChave(), idTerm), doc);
                indexWriter.commit();
                indexWriter.close();
            } catch (Exception exception) {
                Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE,
                        null, exception);
            }
        }
    }

    private List<DomainType> luceneExecutarQuery(Query q) {
        try {
            if (q != null) {
                int hitsPerPage = 10;
                IndexReader reader = IndexReader.open(directory);
                IndexSearcher searcher = new IndexSearcher(reader);

                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                searcher.search(q, collector);
                ScoreDoc[] hits = collector.topDocs().scoreDocs;

                List<DomainType> objList = new ArrayList<DomainType>();
                for (int i = 0; i < hits.length; ++i) {
                    int docId = hits[i].doc;
                    Document d = searcher.doc(docId);
                    KeyType id = (KeyType) d.get(getLuceneIndiceChave());
                    objList.add(super.load(id));
                }
                return objList;
            }
        } catch (Exception exception) {
            Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE,
                    null, exception);
        }
        return new ArrayList<DomainType>();
    }

    public List<DomainType> luceneFiltrarTexto(String indice, String query) {
        Query q = null;
        try {
            q = new QueryParser(Constantes.getLuceneVersion(), indice, analyzer).parse(query);
        } catch (ParseException ex) {
            Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE, null, ex);
        }
        return luceneExecutarQuery(q);
    }

    @Override
    public List<DomainType> findAll() {
        javax.persistence.Query qry = createQuery("select this from " + getBeanClass().getSimpleName() + " this");
        return qry.getResultList();
    }

    @Override
    public void update(DomainType entity) {
        super.update(entity);
        luceneAtualizar(entity);
    }

    @Override
    public void insert(DomainType entity) {
        super.insert(entity);
        luceneSalvar(entity);
    }

    @Override
    public void delete(KeyType id) {
        super.delete(id);
        luceneExcluir(id);
    }
 
}
