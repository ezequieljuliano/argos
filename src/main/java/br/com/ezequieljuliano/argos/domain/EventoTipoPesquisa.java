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
import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Named
public enum EventoTipoPesquisa implements Serializable {
    
    etpTudo("Tudo"), etpEventoMensagem("Mensagem do Evento"), etpHostName("Nome do Host"),
    etpHostUser("Usuário do Host"), etpHostIp("IP do Host"), etpFonte("Fonte"),
    etpNome("Nome do Evento"), etpOcorrenciaDtHr("Data/Hora Ocorrência"), etpPalavrasChave("Palavras-Chave"),
    etpEntidadeNome("Entidade Nome"), etpEntidadeCadastroNacional("Entidade Cadastro Nacional"),
    etpEventoNivelDescricao("Nível de Evento Descrição"), etpEventoTipoDescricao("Tipo de Evento Descrição");
    
    private static final long serialVersionUID = 1L;
    
    private String nome;

    private EventoTipoPesquisa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getLuceneIndex() {
        switch (this) {
            case etpTudo:
                return Constantes.INDICE_EVENTO_TUDO;
            case etpEventoMensagem:
                return Constantes.INDICE_EVENTO_MENSAGEM;
            case etpHostName:
                return Constantes.INDICE_EVENTO_HOSTNAME;
            case etpHostUser:
                return Constantes.INDICE_EVENTO_HOSTUSER;
            case etpHostIp:
                return Constantes.INDICE_EVENTO_HOSTIP;
            case etpFonte:
                return Constantes.INDICE_EVENTO_FONTE;
            case etpNome:
                return Constantes.INDICE_EVENTO_NOME;
            case etpOcorrenciaDtHr:
                return Constantes.INDICE_EVENTO_OCORRENCIADTHR;
            case etpPalavrasChave:
                return Constantes.INDICE_EVENTO_PALAVRASCHAVE;
            case etpEntidadeNome:
                return Constantes.INDICE_ENTIDADE_NOME;
            case etpEntidadeCadastroNacional:
                return Constantes.INDICE_ENTIDADE_CADASTRONACIONAL;
            case etpEventoNivelDescricao:
                return Constantes.INDICE_EVENTO_NIVELDESCRICAO;
            case etpEventoTipoDescricao:
                return Constantes.INDICE_EVENTO_TIPODESCRICAO;
            default:
                return Constantes.INDICE_EVENTO_TUDO;
        }
    }
}
