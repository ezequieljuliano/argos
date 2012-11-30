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
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Embeddable
@NoSql(dataType = "EventoValorCustomizado", dataFormat = DataFormatType.MAPPED)
public class EventoValorCustomizado implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinField(name="eventoCampoCustomizadoId")
    private EventoCampoCustomizado eventoCampoCustomizado;
    
    @Basic
    @Field(name = "valor")
    private String valor;

    public EventoValorCustomizado() {
        super();
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
