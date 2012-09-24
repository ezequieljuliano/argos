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
package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.business.EntidadeBC;
import br.com.ezequieljuliano.argos.business.EventoBC;
import br.com.ezequieljuliano.argos.business.EventoLuceneBC;
import br.com.ezequieljuliano.argos.business.EventoNivelBC;
import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.domain.EventoTipoPesquisa;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.apache.lucene.queryParser.ParseException;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ViewController
public class EventoPesquisaMB {

    private static final long serialVersionUID = 1L;
    @Inject
    private EventoLuceneBC luceneBC;
    @Inject
    private EventoBC eventoBC;
    @Inject
    private EntidadeBC entidadeBC;
    @Inject
    private EventoNivelBC eventoNivelBC;
    @Inject
    private EventoTipoBC eventoTipoBC;
    
    private String campoPesquisa;
    private EventoTipoPesquisa tipoPesquisa;
    private List<Evento> eventos;
    private Evento evento;
    private Boolean pesquisaAvancada;
    private Entidade selectedEntidade;
    private EventoNivel selectedEventoNivel;
    private EventoTipo selectedEventoTipo;

    public EventoPesquisaMB() {
        tipoPesquisa = EventoTipoPesquisa.etpTudo;
        pesquisaAvancada = false;
    }

    public String getCampoPesquisa() {
        return campoPesquisa;
    }

    public void setCampoPesquisa(String campoPesquisa) {
        this.campoPesquisa = campoPesquisa;
    }

    public EventoTipoPesquisa getTipoPesquisa() {
        return tipoPesquisa;
    }

    public void setTipoPesquisa(EventoTipoPesquisa tipoPesquisa) {
        this.tipoPesquisa = tipoPesquisa;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Evento getEvento() {
        if (evento == null) {
            evento = new Evento();
        }
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Boolean getPesquisaAvancada() {
        return pesquisaAvancada;
    }

    public void setPesquisaAvancada(Boolean pesquisaAvancada) {
        this.pesquisaAvancada = pesquisaAvancada;
    }

    public void ativarPesquisaAvancada() {
        this.pesquisaAvancada = true;
    }

    public void inativarPesquisaAvancada() {
        this.pesquisaAvancada = false;
    }

    public Entidade getSelectedEntidade() {
        if (selectedEntidade == null) {
            selectedEntidade = new Entidade();
        }
        return selectedEntidade;
    }

    public void setSelectedEntidade(Entidade selectedEntidade) {
        this.selectedEntidade = selectedEntidade;
    }

    public EventoNivel getSelectedEventoNivel() {
        if (selectedEventoNivel == null) {
            selectedEventoNivel = new EventoNivel();
        }
        return selectedEventoNivel;
    }

    public void setSelectedEventoNivel(EventoNivel selectedEventoNivel) {
        this.selectedEventoNivel = selectedEventoNivel;
    }

    public EventoTipo getSelectedEventoTipo() {
        if (selectedEventoTipo == null) {
            selectedEventoTipo = new EventoTipo();
        }
        return selectedEventoTipo;
    }

    public void setSelectedEventoTipo(EventoTipo selectedEventoTipo) {
        this.selectedEventoTipo = selectedEventoTipo;
    }

    public void pesquisar() {
        if (campoPesquisa == null || campoPesquisa.length() == 0) {
            eventos = eventoBC.findAll();
        } else {
            switch (tipoPesquisa) {
                case etpTudo:
                    try {
                        eventos = luceneBC.findByTudo(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpComputadorGerador:
                    try {
                        eventos = luceneBC.findByComputadorGerador(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpEntidadeNome:
                    try {
                        eventos = luceneBC.findByEntidadeNome(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpEventoNivelDescricao:
                    try {
                        eventos = luceneBC.findByEventoNivelDescricao(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpEventoTipoDescricao:
                    try {
                        eventos = luceneBC.findByEventoTipoDescricao(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpFonte:
                    try {
                        eventos = luceneBC.findByFonte(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpNome:
                    try {
                        eventos = luceneBC.findByNome(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpOcorrenciaData:
                    try {
                        eventos = luceneBC.findByOcorrenciaData(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpPalavrasChave:
                    try {
                        eventos = luceneBC.findByPalavrasChave(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpUsuarioGerador:
                    try {
                        eventos = luceneBC.findByUsuarioGerador(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case etpEventoDescricao:
                    try {
                        eventos = luceneBC.findByEventoDescricao(campoPesquisa);
                    } catch (ParseException ex) {
                        Logger.getLogger(EventoPesquisaMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        }
    }

    public List<SelectItem> getTipoPesquisas() {
        List<SelectItem> itens = new ArrayList<SelectItem>();

        for (EventoTipoPesquisa eventotipoPesquisa : EventoTipoPesquisa.values()) {
            itens.add(new SelectItem(eventotipoPesquisa, eventotipoPesquisa.getNome()));
        }
        return itens;
    }

    public List<Entidade> getEntidadeList() {
        return entidadeBC.findAll();
    }

    public List<EventoNivel> getEventoNivelList() {
        return eventoNivelBC.findAll();
    }

    public List<EventoTipo> getEventoTipoList() {
        return eventoTipoBC.findAll();
    }
}
