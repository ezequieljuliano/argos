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

    occurrence("Ocorrência Dt/Hr", "occurrence"),
    host("Host", "host"),
    keywords("Palavras-Chave", "keywords"),
    owner("Proprietário", "owner"),
    message("Mensagem", "message"),
    markerId("Marcador Id.", "marker._id"),
    markerName("Marcador Nome", "marker.name"),
    levelId("Nível Id.", "level._id"),
    levelName("Nível Nome", "level.name"),
    entityId("Entidade Id.", "entity._id"),
    entityExternalKey("Entidade Chave Externa", "entity.externalKey"),
    entityName("Entidade Nome", "entity.name"),
    userId("Usuário Id.", "user._id"),
    userName("Usuário Nome", "user.name"),
    userEmail("Usuário E-mail", "user.email"),
    userIdentifierKey("Usuário Chave Id.", "user.identifierKey"),
    fullText("Tudo", "fullText");

    private final String description;
    private final String field;

    private Term(String description, String field) {
        this.description = description;
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public String getField() {
        return field;
    }

}
