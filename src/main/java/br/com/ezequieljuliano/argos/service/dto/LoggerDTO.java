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
package br.com.ezequieljuliano.argos.service.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoggerDTO {

    private String occurrence;

    private String host;

    private String keywords;

    private String owner;

    private String message;

    private MarkerDTO marker;

    private LevelDTO level;

    private EntityDTO entity;

    public LoggerDTO() {

    }

    public String getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(String occurrence) {
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

    public MarkerDTO getMarker() {
        return marker;
    }

    public void setMarker(MarkerDTO marker) {
        this.marker = marker;
    }

    public LevelDTO getLevel() {
        return level;
    }

    public void setLevel(LevelDTO level) {
        this.level = level;
    }

    public EntityDTO getEntity() {
        return entity;
    }

    public void setEntity(EntityDTO entity) {
        this.entity = entity;
    }

}
