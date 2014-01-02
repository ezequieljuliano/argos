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

import br.com.ezequieljuliano.argos.util.DateUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Logger.COLLECTION_NAME)
public class Logger implements Serializable {

    public static final String COLLECTION_NAME = "Logger";

    @Id
    private String id;

    private Date occurrence;

    private String host;

    private String keywords;

    private String owner;

    private String message;

    private String fullText;

    private Marker marker;

    private Level level;

    private Entity entity;

    private User user;

    public Logger() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Date occurrence) {
        this.occurrence = occurrence;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void generateFullText() {
        this.fullText = this.toString();
    }

    public String getFullText() {
        return fullText;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Logger other = (Logger) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String separator = System.getProperty("line.separator");
        StringBuilder str = new StringBuilder();
        str.append("Id: ").append(id).append(separator);
        str.append("Ocorrência: ").append(DateUtil.dateTimeToString(occurrence)).append(separator);
        str.append("Host: ").append(host).append(separator);
        str.append("Palavras-Chave: ").append(keywords).append(separator);
        str.append("Proprietário: ").append(owner).append(separator);
        str.append("Mensagem: ").append(message).append(separator);
        str.append("Marcador: ").append(marker.getId()).append(separator);
        str.append("Marcador Nome: ").append(marker.getName()).append(separator);
        str.append("Nível: ").append(level.getId()).append(separator);
        str.append("Nível Nome: ").append(level.getName()).append(separator);
        str.append("Entidade: ").append(entity.getId()).append(separator);
        str.append("Entidade Chave Externa: ").append(entity.getExternalKey()).append(separator);
        str.append("Entidade Nome: ").append(entity.getName()).append(separator);
        str.append("Usuário: ").append(user.getId()).append(separator);
        str.append("Usuário Nome: ").append(user.getUserName()).append(separator);
        str.append("Usuário E-mail: ").append(user.getEmail()).append(separator);
        str.append("Usuário Chave Identificadora: ").append(user.getIdentifierKey()).append(separator);
        return str.toString();
    }

}
