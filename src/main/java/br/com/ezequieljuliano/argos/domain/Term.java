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

    occurrence("Ocorrência Dt/Hr", "occurrence", false),
    host("Host", "host", false),
    keywords("Palavras-Chave", "keywords", false),
    owner("Proprietário", "owner", false),
    message("Mensagem", "message", false),
    markerId("Marcador Id.", "marker._id", true),
    markerName("Marcador Nome", "marker.name", false),
    levelId("Nível Id.", "level._id", true),
    levelName("Nível Nome", "level.name", false),
    entityId("Entidade Id.", "entity._id", true),
    entityExternalKey("Entidade Chave Externa", "entity.externalKey", false),
    entityName("Entidade Nome", "entity.name", false),
    userId("Usuário Id.", "user._id", true),
    userName("Usuário Nome", "user.name", false),
    userEmail("Usuário E-mail", "user.email", false),
    userIdentifierKey("Usuário Chave Id.", "user.identifierKey", false),
    fullText("Tudo", "fullText", false);

    private final String description;
    private final String field;
    private final boolean objId;

    private Term(String description, String field, boolean objId) {
        this.description = description;
        this.field = field;
        this.objId = objId;
    }

    public String getDescription() {
        return description;
    }

    public String getField() {
        return field;
    }

    public boolean isObjId() {
        return objId;
    }

}
