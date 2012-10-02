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

import java.io.Serializable;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class EventoPesquisaFiltro implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private EventoTipoPesquisa filtroTipo;
    private String pesquisaValor;
    private Entidade entidade;
    private EventoNivel eventoNivel;
    private EventoTipo eventoTipo;

    public EventoPesquisaFiltro() {
    }

    public EventoPesquisaFiltro(EventoTipoPesquisa filtroTipo, String pesquisaValor, Entidade entidade, EventoNivel eventoNivel, EventoTipo eventoTipo) {
        this.filtroTipo = filtroTipo;
        this.pesquisaValor = pesquisaValor;
        this.entidade = entidade;
        this.eventoNivel = eventoNivel;
        this.eventoTipo = eventoTipo;
    }

    public String getPesquisaValor() {
        return pesquisaValor;
    }

    public void setPesquisaValor(String pesquisaValor) {
        this.pesquisaValor = pesquisaValor;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public EventoNivel getEventoNivel() {
        return eventoNivel;
    }

    public void setEventoNivel(EventoNivel eventoNivel) {
        this.eventoNivel = eventoNivel;
    }

    public EventoTipo getEventoTipo() {
        return eventoTipo;
    }

    public void setEventoTipo(EventoTipo eventoTipo) {
        this.eventoTipo = eventoTipo;
    }

    public EventoTipoPesquisa getFiltroTipo() {
        return filtroTipo;
    }

    public void setFiltroTipo(EventoTipoPesquisa filtroTipo) {
        this.filtroTipo = filtroTipo;
    }
}
