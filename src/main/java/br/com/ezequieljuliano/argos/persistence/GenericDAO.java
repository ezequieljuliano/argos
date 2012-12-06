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
import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
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
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
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
    public abstract Filter getLuceneFiltroDeRestricao();

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
                //Resultados por páginas de documentos
                int hitsPerPage = 10;
                //Abre o diretório dos índices
                IndexReader reader = IndexReader.open(directory);
                //Cria o buscador dos documentos indexados
                IndexSearcher searcher = new IndexSearcher(reader);
                //Coletor de resultados
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                //Verifica se existe filtragem
                //Filtragem é um processo que restringe o espaço de procura e permite que apenas um 
                //subconjunto de documentos seja considerado para as ocorrências de procura.
                Filter filter = getLuceneFiltroDeRestricao();
                if (filter != null) {
                    searcher.search(q, filter, collector);
                } else {
                    searcher.search(q, collector);
                }
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
    
    /*
     * Tipo de consulta mais básico para procurar um índice. TermQuery pode ser construído usando um termo único.
     */
    public List<DomainType> luceneTermQuery(String indice, String texto) {
        Term term = new Term(indice, texto);
        Query query = new TermQuery(term);
        return luceneExecutarQuery(query);
    }
    
     /*
     * Você pode procurar usando uma palavra prefixada com o PrefixQuery, que é usado para contruir uma 
     * consulta que corresponda aos documentos que contêm os termos que iniciam com um prefixo de palavra especificada. 
     */
    public List<DomainType> lucenePrefixQuery(String indice, String texto) {
        Term term = new Term(indice, texto);
        Query query = new PrefixQuery(term);
        return luceneExecutarQuery(query);
    }
    
     /*
     * Um WildcardQuery implementa uma consulta com caractere curinga, podendo fazer procuras como
     * arch* (permitindo localizar documentos que contém architect, architecture, etc.). Dois caracteres curingas padrão são usados:
        * para zero ou mais
        ? para um ou mais
     */
    public List<DomainType> luceneWildCardQuery(String indice, String texto) {
        Term term = new Term(indice, texto);
        Query query = new WildcardQuery(term);
        return luceneExecutarQuery(query);
    }
    
     /*
     *  Você pode procurar por termos semelhantes com o FuzzyQuery, que corresponde às palavras 
     *  que são semelhantes a sua palavra especificada.
     */
    public List<DomainType> luceneFuzzyQuery(String indice, String texto) {
        Term term = new Term(indice, texto);
        Query query = new FuzzyQuery(term);
        return luceneExecutarQuery(query);
    }
    
     /*
     * QueryParser é útil para analisar cadeias de consultas inseridas pelo usuário. 
     * Ele pode ser usado para analisar expressões de consultas inseridas pelo usuário em um objeto de consulta do Lucene, 
     * que pode ser transmitido para o método de procura do IndexSearcher.Ele pode analisar expressões de consultas completas. 
     * QueryParser converte internamente uma cadeia de consulta inserida pelo usuário em uma das subclasses de consulta concretas. 
     * É necessário escapar caracteres especiais, como *, ? com uma barra invertida (\). Você pode construir consultas 
     * booleanas textualmente usando os operadores AND, OR e NOT.
     */
    public List<DomainType> luceneParserQuery(String indice, String texto) {
        Query query = null;
        try {
            query = new QueryParser(Constantes.getLuceneVersion(), indice, analyzer).parse(texto);
        } catch (ParseException ex) {
            Logger.getLogger(getBeanClass().getSimpleName()).log(Level.SEVERE, null, ex);
        }
        return luceneExecutarQuery(query);
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
