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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;

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

    public abstract Document luceneCriarDocumento(DomainType obj);

    public void luceneSalvar(DomainType obj) {
        Document doc = luceneCriarDocumento(obj);
        if (doc != null) {
            IndexWriterConfig indexConfig = new IndexWriterConfig(Constantes.getLuceneVersion(), analyzer);
            IndexWriter indexWriter;
            try {
                indexWriter = new IndexWriter(directory, indexConfig);
                indexWriter.addDocument(doc);
                indexWriter.commit();
                indexWriter.close();
            } catch (Exception exception) {
                Logger.getLogger(EventoLuceneDAO.class.getName()).log(Level.SEVERE,
                        null, exception);
            }
        }
    }
    
    private List<DomainType> luceneExecuteQuery(Query q){
        
        return null;    
    }
}
