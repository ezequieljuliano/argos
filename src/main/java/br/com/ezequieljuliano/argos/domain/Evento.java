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
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.Field;
import org.eclipse.persistence.nosql.annotations.JoinField;
import org.eclipse.persistence.nosql.annotations.NoSql;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Entity
@NoSql(dataType="Evento", dataFormat=DataFormatType.MAPPED)
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue
    @Field(name="_id")
    private String id;
    
    @Basic
    @Field(name="hostName")
    private String hostName;
    
    @Basic
    @Field(name="hostIp")
    private String hostIp;
    
    @Basic
    @Field(name="hostUser")
    private String hostUser;
    
    @Basic
    @Field(name="mensagem")
    private String mensagem;
    
    @Basic
    @Field(name="fonte")
    private String fonte;
    
    @Basic
    @Field(name="nome")
    private String nome;
    
    @Basic
    @Field(name="ocorrenciaDtHr")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ocorrenciaDtHr;
    
    @Basic
    @Field(name="palavrasChave")
    private String palavrasChave;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinField(name="entidadeId")
    private Entidade entidade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinField(name="eventoNivelId")
    private EventoNivel eventoNivel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinField(name="eventoTipoId")
    private EventoTipo eventoTipo;

    public Evento() {
        super();    
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getHostUser() {
        return hostUser;
    }

    public void setHostUser(String hostUser) {
        this.hostUser = hostUser;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getFonte() {
        return fonte;
    }

    public void setFonte(String fonte) {
        this.fonte = fonte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getOcorrenciaDtHr() {
        return ocorrenciaDtHr;
    }

    public void setOcorrenciaDtHr(Date ocorrenciaDtHr) {
        this.ocorrenciaDtHr = ocorrenciaDtHr;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public EventoNivel getEventoNivel() {
        return eventoNivel;
    }

    public void setEventoNivel(EventoNivel eventoNivel) {
        this.eventoNivel = eventoNivel;
    }

    public EventoTipo getEventoTipo() {
        return eventoTipo;
    }

    public void setEventoTipo(EventoTipo eventoTipo) {
        this.eventoTipo = eventoTipo;
    }

    @Override
    public boolean equals(Object obj) {
         if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
