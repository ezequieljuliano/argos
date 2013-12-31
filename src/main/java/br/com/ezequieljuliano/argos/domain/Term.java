/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
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

public enum Term implements Serializable {

    occurrence("Ocorrência Dt/Hr"),
    host("Host"),
    keywords("Palavras-Chave"),
    owner("Proprietário"),
    message("Mensagem"),
    markerId("Marcador Id."),
    markerName("Marcador Nome"),
    levelId("Nível Id."),
    levelName("Nível Nome"),
    entityId("Entidade Id."),
    entityExternalKey("Entidade Chave Externa"),
    entityName("Entidade Nome"),
    userId("Usuário Id."),
    userName("Usuário Nome"),
    userEmail("Usuário E-mail"),
    userIdentifierKey("Usuário Chave Id."),
    fullText("Tudo");

    private final String description;

    private Term(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
