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
import br.com.ezequieljuliano.argos.business.EventoNivelBC;
import br.com.ezequieljuliano.argos.business.EventoTipoBC;
import br.com.ezequieljuliano.argos.domain.Entidade;
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoNivel;
import br.com.ezequieljuliano.argos.domain.EventoPesquisaFiltro;
import br.com.ezequieljuliano.argos.domain.EventoTermoPesquisa;
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.security.SessionAttributes;
import br.com.ezequieljuliano.argos.domain.FiltroTermo;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.util.Strings;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ViewController
public class EventoPesquisaMB {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private EventoBC eventoBC;
    
    @Inject
    private EntidadeBC entidadeBC;
    
    @Inject 
    private EventoNivelBC eventoNivelBC;
    
    @Inject
    private EventoTipoBC eventoTipoBC;
    
    @Inject
    private MessageContext messageContext;
    
    @Inject
    private SessionAttributes sessionAttributes;
    
    private Boolean pesquisaAvancada;
    private EventoTermoPesquisa termoPesquisa;
    private Object valorPesquisa;
    private List<FiltroTermo> termosDePesquisa = new ArrayList<FiltroTermo>();
    private List<Evento> eventos = new ArrayList<Evento>();
    private Evento evento;

    public EventoPesquisaMB() {
        this.termoPesquisa = EventoTermoPesquisa.etpTudo;
        this.pesquisaAvancada = false;
        this.valorPesquisa = null;
    }

    public List<Evento> getEventos() {
        return this.eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public Evento getEvento() {
        if (this.evento == null) {
            this.evento = new Evento();
        }
        return this.evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Boolean getPesquisaAvancada() {
        return this.pesquisaAvancada;
    }

    public void setPesquisaAvancada(Boolean pesquisaAvancada) {
        this.pesquisaAvancada = pesquisaAvancada;
    }

    public void ativarPesquisaAvancada() {
        this.pesquisaAvancada = true;
        this.termosDePesquisa = new ArrayList<FiltroTermo>();
        this.termoPesquisa = EventoTermoPesquisa.etpTudo;
        this.valorPesquisa = null;
        this.eventos = new ArrayList<Evento>();
    }

    public void inativarPesquisaAvancada() {
        this.pesquisaAvancada = false;
        this.termosDePesquisa = new ArrayList<FiltroTermo>();
        this.termoPesquisa = EventoTermoPesquisa.etpTudo;
        this.valorPesquisa = null;
        this.eventos = new ArrayList<Evento>();
    }

    public EventoTermoPesquisa getTermoPesquisa() {
        return this.termoPesquisa;
    }

    public void setTermoPesquisa(EventoTermoPesquisa termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public Object getValorPesquisa() {
        return this.valorPesquisa;
    }

    public void setValorPesquisa(Object valorPesquisa) {
        this.valorPesquisa = valorPesquisa;
    }

    public List<SelectItem> getListTermosPesquisaModoAvancado() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (EventoTermoPesquisa eventotipoPesquisa : EventoTermoPesquisa.values()) {
            itens.add(new SelectItem(eventotipoPesquisa, eventotipoPesquisa.getNome()));
        }
        return itens;
    }

    public List<SelectItem> getListTermosPesquisaModoBasico() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (EventoTermoPesquisa eventotipoPesquisa : EventoTermoPesquisa.values()) {
            if (eventotipoPesquisa.getModoBasico()) {
                itens.add(new SelectItem(eventotipoPesquisa, eventotipoPesquisa.getNome()));
            }
        }
        return itens;
    }

    public List<FiltroTermo> getTermosDePesquisa() {
        return this.termosDePesquisa;
    }

    public void addTermoDePesquisa() {
        if ((this.termoPesquisa == null) || (this.valorPesquisa == null) || Strings.isEmpty(this.valorPesquisa.toString())) {
            messageContext.add("Selecione um termo e informe um valor!", SeverityType.ERROR);
        } else {
            this.termosDePesquisa.add(new FiltroTermo(this.termoPesquisa, this.valorPesquisa));
            this.termoPesquisa = EventoTermoPesquisa.etpTudo;
            this.valorPesquisa = null;
        }
    }

    public void removeTermoDePesquisa(FiltroTermo termoPesquisa) {
        this.termosDePesquisa.remove(termoPesquisa);
        this.termoPesquisa = EventoTermoPesquisa.etpTudo;
        this.valorPesquisa = null;
    }

    public void pesquisar() throws Exception {
        if (this.pesquisaAvancada) {
            if (this.termosDePesquisa.isEmpty()) {
                messageContext.add("Selecione um termo e informe um valor!", SeverityType.WARN);
            } else {
                EventoPesquisaFiltro filtro = new EventoPesquisaFiltro(this.termosDePesquisa, sessionAttributes.getUsuario());
                this.eventos = eventoBC.findByPesquisaFiltro(filtro);
            }
        } else {
            if ((this.termoPesquisa == null) || (this.valorPesquisa == null) || Strings.isEmpty(this.valorPesquisa.toString())) {
                messageContext.add("Selecione um termo e informe um valor!", SeverityType.WARN);
            } else {
                try {
                    this.termosDePesquisa = new ArrayList<FiltroTermo>();
                    this.termosDePesquisa.add(new FiltroTermo(this.termoPesquisa, this.valorPesquisa));
                    EventoPesquisaFiltro filtro = new EventoPesquisaFiltro(this.termosDePesquisa, sessionAttributes.getUsuario());
                    this.eventos = eventoBC.findByPesquisaFiltro(filtro);
                } catch (Exception e) {
                    messageContext.add(e.getMessage(), SeverityType.ERROR);
                }
            }
        }
    }
    
    public Boolean isFiltroEntidade() {
        return (this.termoPesquisa == EventoTermoPesquisa.etpEntidade);
    }

    public Boolean isFiltroEventoNivel() {
        return (this.termoPesquisa == EventoTermoPesquisa.etpEventoNivel);
    }

    public Boolean isFiltroEventoTipo() {
        return (this.termoPesquisa == EventoTermoPesquisa.etpEventoTipo);
    }

    public Boolean isDemaisFitros() {
        return (!isFiltroEntidade() && !isFiltroEventoNivel() && !isFiltroEventoTipo());
    }
    
    public List<Entidade> getListEntidades(){
        Usuario usuario = sessionAttributes.getUsuario();
        return entidadeBC.findByUsuario(usuario);
    }
    
    public List<EventoNivel> getListEventoNiveis(){
        return eventoNivelBC.findAll();
    }
    
    public List<EventoTipo> getListEventoTipos(){
       return eventoTipoBC.findAll();
    }
    
}
