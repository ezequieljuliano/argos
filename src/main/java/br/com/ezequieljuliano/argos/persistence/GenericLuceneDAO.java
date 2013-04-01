/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ezequieljuliano.argos.persistence;

import br.com.ezequieljuliano.argos.constant.Constantes;
import br.com.ezequieljuliano.argos.util.LuceneAnalyzerUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Repository
public abstract class GenericLuceneDAO<DomainType, KeyType> extends GenericDAO<DomainType, KeyType>{
    
    private static final long serialVersionUID = 1L;
    
    private Analyzer analyzer = LuceneAnalyzerUtil.get();
    
    @Autowired
    private Directory directory;

    //Métodos que devem ser escritos nas classes extendidas
    public abstract Document getLuceneDocument(DomainType obj);
    public abstract String getLuceneIndexKey();
    public abstract String getLuceneContentString(DomainType obj);
    public abstract Filter getLuceneFilterRestriction();
    public abstract KeyType getValueIdKey(DomainType obj);
    
    private IndexWriter getIndexWriter() {
        try {
            IndexWriterConfig indexConfig = new IndexWriterConfig(Constantes.getLuceneVersion(), analyzer);
            return new IndexWriter(directory, indexConfig);
        } catch (CorruptIndexException ex) {
            Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE, null, ex);
        } catch (LockObtainFailedException ex) {
            Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void luceneSave(DomainType obj) {
        Document doc = getLuceneDocument(obj);
        if (doc != null) {
            IndexWriter indexWriter;
            try {
                indexWriter = getIndexWriter();
                indexWriter.addDocument(doc);
                indexWriter.commit();
                indexWriter.close();
            } catch (Exception exception) {
                Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE,
                        null, exception);
            }
        }
    }
    
     private void luceneDelete(KeyType id) {
        IndexWriter indexWriter;
        try {
            //Pega o valor da chave e converter para string
            String idTerm = (String) id;
            indexWriter = getIndexWriter();
            indexWriter.deleteDocuments(new Term(getLuceneIndexKey(), idTerm));
            indexWriter.commit();
            indexWriter.close();
        } catch (Exception exception) {
            Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE,
                    null, exception);
        }
    }

    private void luceneUpdate(DomainType obj) {
        Document doc = getLuceneDocument(obj);
        if (doc != null) {
            IndexWriter indexWriter;
            try {
                //Pega o valor da chave e converter para string
                String idTerm = doc.get(getLuceneIndexKey());
                indexWriter = getIndexWriter();
                indexWriter.updateDocument(new Term(getLuceneIndexKey(), idTerm), doc);
                indexWriter.commit();
                indexWriter.close();
            } catch (Exception exception) {
                Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE,
                        null, exception);
            }
        }
    }

    public List<DomainType> luceneExecQuery(Query q) {
        try {
            if (q != null) {
                //Resultados por páginas de documentos
                int hitsPerPage = 100;
                //Abre o diretório dos índices
                IndexReader reader = IndexReader.open(directory);
                //Cria o buscador dos documentos indexados
                IndexSearcher searcher = new IndexSearcher(reader);
                //Coletor de resultados
                TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
                //Verifica se existe filtragem
                //Filtragem é um processo que restringe o espaço de procura e permite que apenas um 
                //subconjunto de documentos seja considerado para as ocorrências de procura.
                Filter filter = getLuceneFilterRestriction();
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
                    KeyType id = (KeyType) d.get(getLuceneIndexKey());
                    DomainType domain = load(id);
                    if (domain != null) {
                        objList.add(domain);
                    }
                }
                return objList;
            }
        } catch (Exception exception) {
            Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE,
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
        return luceneExecQuery(query);
    }

    /*
     * Você pode procurar usando uma palavra prefixada com o PrefixQuery, que é usado para contruir uma 
     * consulta que corresponda aos documentos que contêm os termos que iniciam com um prefixo de palavra especificada. 
     */
    public List<DomainType> lucenePrefixQuery(String indice, String texto) {
        Term term = new Term(indice, texto);
        Query query = new PrefixQuery(term);
        return luceneExecQuery(query);
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
        return luceneExecQuery(query);
    }

    /*
     *  Você pode procurar por termos semelhantes com o FuzzyQuery, que corresponde às palavras 
     *  que são semelhantes a sua palavra especificada.
     */
    public List<DomainType> luceneFuzzyQuery(String indice, String texto) {
        Term term = new Term(indice, texto);
        Query query = new FuzzyQuery(term);
        return luceneExecQuery(query);
    }

    /*
     * Você pode procurar em um intervalo usando o RangeQuery. Todos os termos são organizados de maneira 
     * lexicográfica no índice. O RangeQuery do Lucene permite que os usuários procurem termos dentro de um 
     * intervalo.O intervalo pode ser especificado usando um termo de início e um termo de encerramento, 
     * que pode ser incluído ou excluído.
     */
    public List<DomainType> luceneLongRangeQuery(String indice, Long inicio, Long fim) {
        NumericRangeQuery<Long> newLongRange = NumericRangeQuery.newLongRange(indice, inicio, fim, true, true);
        return luceneExecQuery(newLongRange);
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
            Logger.getLogger(getDomainClass().getName()).log(Level.SEVERE, null, ex);
        }
        return luceneExecQuery(query);
    }

    @Override
    public void insert(DomainType obj) {
        super.insert(obj);
        luceneSave(obj);
    }
    
    @Override
    public void save(DomainType obj) {
        super.save(obj); 
        luceneUpdate(obj);
    }

    @Override
    public void delete(KeyType id) {
        luceneDelete(id);
        super.delete(id);
    }

    @Override
    public void deleteObj(DomainType obj) {
        luceneDelete(getValueIdKey(obj));
        super.deleteObj(obj); 
    }
    
    
}
