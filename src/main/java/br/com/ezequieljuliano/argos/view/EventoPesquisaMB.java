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
import br.com.ezequieljuliano.argos.domain.EventoTipo;
import br.com.ezequieljuliano.argos.domain.EventoTipoPesquisa;
import br.com.ezequieljuliano.argos.security.SessionAttributes;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
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
        limparFiltroAvancado();
        this.pesquisaAvancada = true;
    }

    public void inativarPesquisaAvancada() {
        limparFiltroAvancado();
        this.pesquisaAvancada = false;
    }

    public Entidade getSelectedEntidade() {
        return selectedEntidade;
    }

    public void setSelectedEntidade(Entidade selectedEntidade) {
        this.selectedEntidade = selectedEntidade;
    }

    public EventoNivel getSelectedEventoNivel() {
        return selectedEventoNivel;
    }

    public void setSelectedEventoNivel(EventoNivel selectedEventoNivel) {
        this.selectedEventoNivel = selectedEventoNivel;
    }

    public EventoTipo getSelectedEventoTipo() {
        return selectedEventoTipo;
    }

    public void setSelectedEventoTipo(EventoTipo selectedEventoTipo) {
        this.selectedEventoTipo = selectedEventoTipo;
    }

    public void pesquisar() throws Exception {
        if (campoPesquisa.equals("") || campoPesquisa.length() == 0) {
            messageContext.add("Informe algum valor no campo de pesquisa!", SeverityType.WARN);
        } else {
            try {
                EventoPesquisaFiltro filtro = new EventoPesquisaFiltro(tipoPesquisa, campoPesquisa,
                        selectedEntidade, selectedEventoNivel, selectedEventoTipo, sessionAttributes.getUsuario());
                eventos = eventoBC.findByPesquisaFiltro(filtro);
            } catch (Exception e) {
                messageContext.add(e.getMessage(), SeverityType.ERROR);
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
        return entidadeBC.findByUsuario(sessionAttributes.getUsuario());
    }

    public List<EventoNivel> getEventoNivelList() {
        return eventoNivelBC.findAll();
    }

    public List<EventoTipo> getEventoTipoList() {
        return eventoTipoBC.findAll();
    }

    private void limparFiltroAvancado() {
        selectedEntidade = null;
        selectedEventoNivel = null;
        selectedEventoTipo = null;
    }
}
