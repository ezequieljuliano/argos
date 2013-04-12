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
package br.com.ezequieljuliano.argos.domain;

import br.com.ezequieljuliano.argos.constant.Constantes;
import br.com.ezequieljuliano.argos.util.LuceneAnalyzerUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class EventoPesquisaFiltro implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<FiltroTermo> termosDePesquisa;
    private Usuario usuario;
    private int numHitsResults;

    public EventoPesquisaFiltro(List<FiltroTermo> termosDePesquisa, Usuario usuario, int numHitsResults) {
        this.termosDePesquisa = termosDePesquisa;
        this.usuario = usuario;
        this.numHitsResults = numHitsResults;
    }

    public List<FiltroTermo> getTermosDePesquisa() {
        return termosDePesquisa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getNumHitsResults() {
        return numHitsResults;
    }

    public BooleanQuery getBooleanQuery() {
        BooleanQuery booleanQuery = new BooleanQuery();
        for (FiltroTermo obj : this.termosDePesquisa) {
            switch (obj.getKey()) {
                case etpEntidade: {
                    Entidade entidade = (Entidade) obj.getValue();
                    booleanQuery.add(new TermQuery(new Term(obj.getKey().getLuceneIndex(), entidade.getId())), BooleanClause.Occur.MUST);
                    break;
                }
                case etpEventoNivel: {
                    EventoNivel eventoNivel = (EventoNivel) obj.getValue();
                    booleanQuery.add(new TermQuery(new Term(obj.getKey().getLuceneIndex(), eventoNivel.getId())), BooleanClause.Occur.MUST);
                    break;
                }
                case etpEventoTipo: {
                    EventoTipo eventoTipo = (EventoTipo) obj.getValue();
                    booleanQuery.add(new TermQuery(new Term(obj.getKey().getLuceneIndex(), eventoTipo.getId())), BooleanClause.Occur.MUST);
                    break;
                }
                default: {
                    String valorPesquisa = (String) obj.getValue();
                    try {
                        booleanQuery.add(new QueryParser(Constantes.getLuceneVersion(), obj.getKey().getLuceneIndex(), LuceneAnalyzerUtil.get()).parse(valorPesquisa), BooleanClause.Occur.SHOULD);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaFiltro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        return booleanQuery;
    }
}
