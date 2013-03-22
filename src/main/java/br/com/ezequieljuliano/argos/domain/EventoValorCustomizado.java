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
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Document
public class EventoValorCustomizado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    @DBRef
    private EventoCampoCustomizado eventoCampoCustomizado;
    
    @Indexed
    private String valor;

    public EventoValorCustomizado() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public EventoCampoCustomizado getEventoCampoCustomizado() {
        return eventoCampoCustomizado;
    }

    public void setEventoCampoCustomizado(EventoCampoCustomizado eventoCampoCustomizado) {
        this.eventoCampoCustomizado = eventoCampoCustomizado;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
 
}
