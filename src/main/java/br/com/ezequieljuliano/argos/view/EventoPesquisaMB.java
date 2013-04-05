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

import br.com.ezequieljuliano.argos.business.EventoBC;
import br.com.ezequieljuliano.argos.domain.Evento;
import br.com.ezequieljuliano.argos.domain.EventoTermoPesquisa;
import br.com.ezequieljuliano.argos.security.SessionAttributes;
import br.com.ezequieljuliano.argos.domain.FiltroTermo;
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
    private MessageContext messageContext;
    
    @Inject
    private SessionAttributes sessionAttributes;
    
    private Boolean pesquisaAvancada;
    private EventoTermoPesquisa termoPesquisa;
    private Object valorPesquisa;
    private List<FiltroTermo> termosDePesquisa = new ArrayList<FiltroTermo>();
    private List<Evento> eventos;
    private Evento evento;

    public EventoPesquisaMB() {
        this.termoPesquisa = EventoTermoPesquisa.etpTudo;
        this.pesquisaAvancada = false;
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

    public EventoTermoPesquisa getTermoPesquisa() {
        return termoPesquisa;
    }

    public void setTermoPesquisa(EventoTermoPesquisa termoPesquisa) {
        this.termoPesquisa = termoPesquisa;
    }

    public Object getValorPesquisa() {
        return valorPesquisa;
    }

    public void setValorPesquisa(Object valorPesquisa) {
        this.valorPesquisa = valorPesquisa;
    }

    public List<SelectItem> getListTermosPesquisa() {
        List<SelectItem> itens = new ArrayList<SelectItem>();
        for (EventoTermoPesquisa eventotipoPesquisa : EventoTermoPesquisa.values()) {
            itens.add(new SelectItem(eventotipoPesquisa, eventotipoPesquisa.getNome()));
        }
        return itens;
    }

    public List<FiltroTermo> getTermosDePesquisa() {
        return termosDePesquisa;
    }

    public void addTermoDePesquisa() {
        if (this.termoPesquisa != null) {
            this.termosDePesquisa.add(new FiltroTermo(this.termoPesquisa, this.valorPesquisa));
            this.termoPesquisa = EventoTermoPesquisa.etpTudo;
            this.valorPesquisa = null;
        } else {
            messageContext.add("Selecione um termo e informe um valor!", SeverityType.ERROR);
        }
    }

    public void removeTermoDePesquisa(FiltroTermo termoPesquisa) {
        this.termosDePesquisa.remove(termoPesquisa);
    }

    public void pesquisar() throws Exception {
        if (valorPesquisa == null || Strings.isEmpty(valorPesquisa.toString())) {
            messageContext.add("Informe algum valor no campo de pesquisa!", SeverityType.WARN);
        } else {
            try {
                //EventoPesquisaFiltro filtro = new EventoPesquisaFiltro(termoPesquisa, valorPesquisa,
                //        selectedEntidade, selectedEventoNivel, selectedEventoTipo, sessionAttributes.getUsuario());
                //eventos = eventoBC.findByPesquisaFiltro(filtro);
            } catch (Exception e) {
                messageContext.add(e.getMessage(), SeverityType.ERROR);
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
    
}
