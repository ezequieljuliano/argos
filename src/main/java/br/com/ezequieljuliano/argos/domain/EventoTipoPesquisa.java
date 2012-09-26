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
import javax.inject.Named;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Named
public enum EventoTipoPesquisa {

    etpTudo("Tudo"), etpComputadorGerador("Computador Gerador"), etpFonte("Fonte"),
    etpNome("Nome"), etpOcorrenciaData("Data de Ocorrência"), etpPalavrasChave("Palavras-Chave"),
    etpUsuarioGerador("Usuário Gerador"), etpEntidadeNome("Entidade"),
    etpEventoNivelDescricao("Nível de Evento"), etpEventoTipoDescricao("Tipo de Evento"),
    etpEventoDescricao("Descrição do Evento");
    private String nome;

    private EventoTipoPesquisa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLuceneIndex() {
        switch (this) {
            case etpTudo:
                return Constantes.TUDO;
            case etpComputadorGerador:
                return Constantes.INDICE_COMPUTADOR_GERADOR;
            case etpFonte:
                return Constantes.INDICE_FONTE;
            case etpNome:
                return Constantes.INDICE_NOME;
            case etpOcorrenciaData:
                return Constantes.INDICE_OCORRENCIA_DATA;
            case etpPalavrasChave:
                return Constantes.INDICE_PALAVRAS_CHAVE;
            case etpUsuarioGerador:
                return Constantes.INDICE_USUARIO_GERADOR;
            case etpEntidadeNome:
                return Constantes.INDICE_ENTIDADE_NOME;
            case etpEventoNivelDescricao:
                return Constantes.INDICE_EVENTO_NIVEL_DESCRICAO;
            case etpEventoTipoDescricao:
                return Constantes.INDICE_EVENTO_TIPO_DESCRICAO;
            case etpEventoDescricao:
                return Constantes.INDICE_EVENTO_DESCRICAO;
            default:
                return Constantes.TUDO;
        }
    }
}
