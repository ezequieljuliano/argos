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
    
    private EventoTermoPesquisa termoDoFiltro;
    private String textoDaPesquisa;
    private Entidade entidade;
    private EventoNivel eventoNivel;
    private EventoTipo eventoTipo;
    private Usuario usuario;

    public EventoPesquisaFiltro(EventoTermoPesquisa termoDoFiltro, String textoDaPesquisa,
            Entidade entidade, EventoNivel eventoNivel, EventoTipo eventoTipo, Usuario usuario) {
        this.usuario = usuario;
        this.termoDoFiltro = termoDoFiltro;
        this.textoDaPesquisa = textoDaPesquisa;
        this.entidade = entidade;
        this.eventoNivel = eventoNivel;
        this.eventoTipo = eventoTipo;
    }

    public EventoTermoPesquisa getTermoDoFiltro() {
        return termoDoFiltro;
    }

    public String getTextoDaPesquisa() {
        return textoDaPesquisa;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public EventoNivel getEventoNivel() {
        return eventoNivel;
    }

    public EventoTipo getEventoTipo() {
        return eventoTipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
