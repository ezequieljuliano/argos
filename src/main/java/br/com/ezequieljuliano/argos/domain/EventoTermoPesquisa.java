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
public enum EventoTermoPesquisa implements Serializable {

    etpTudo("Tudo", Boolean.TRUE), etpEventoMensagem("Mensagem do Evento", Boolean.TRUE), etpHostName("Nome do Host", Boolean.TRUE),
    etpHostUser("Usuário do Host", Boolean.TRUE), etpHostMac("MAC do Host", Boolean.TRUE), etpSysUser("Usuário Sistema", Boolean.TRUE),
    etpHostIp("IP do Host", Boolean.TRUE), etpFonte("Fonte", Boolean.TRUE),
    etpNome("Nome do Evento", Boolean.TRUE), etpOcorrenciaDtHr("Data/Hora Ocorrência", Boolean.TRUE), etpPalavrasChave("Palavras-Chave", Boolean.TRUE),
    etpEntidade("Entidade", Boolean.FALSE), etpEventoNivel("Evento Nível", Boolean.FALSE), etpEventoTipo("Evento Tipo", Boolean.FALSE);
    private static final long serialVersionUID = 1L;
    private String nome;
    private Boolean modoBasico;

    private EventoTermoPesquisa(String nome, Boolean modoBasico) {
        this.nome = nome;
        this.modoBasico = modoBasico;
    }

    public String getNome() {
        return nome;
    }

    public Boolean getModoBasico() {
        return modoBasico;
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
            case etpHostMac:
                return Constantes.INDICE_EVENTO_HOSTMAC;
            case etpSysUser:
                return Constantes.INDICE_EVENTO_SYSUSER;
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
            case etpEntidade:
                return Constantes.INDICE_EVENTO_ENTIDADEID;
            case etpEventoNivel:
                return Constantes.INDICE_EVENTO_NIVELID;
            case etpEventoTipo:
                return Constantes.INDICE_EVENTO_TIPOID;
            default:
                return Constantes.INDICE_EVENTO_TUDO;
        }
    }
}
