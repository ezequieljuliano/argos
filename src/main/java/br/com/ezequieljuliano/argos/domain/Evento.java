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
import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Entity
@Table(name = "EVENTO", schema = "Argos@cassandra_pu")
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "EVENTO_ID")
    private String id;
    
    @Column(name = "HOST_NAME")
    private String hostName;
    
    @Column(name = "HOST_IP")
    private String hostIp;
    
    @Column(name = "HOST_USER")
    private String hostUser;
    
    @Column(name = "MENSAGEM")
    private String mensagem;
    
    @Column(name = "FONTE")
    private String fonte;
    
    @Column(name = "NOME")
    private String nome;
    
    @Column(name = "OCORRENCIA_DTHR")
    private java.sql.Timestamp ocorrenciaDtHr;
    
    @Column(name = "PALAVRAS_CHAVE")
    private String palavrasChave;
    
    @ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="ENTIDADE_ID")
    private Entidade entidade;
    
    @ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="EVENTO_NIVEL_ID")
    private EventoNivel eventoNivel;
    
    @ManyToOne(cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="EVENTO_TIPO_ID")
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

    public Timestamp getOcorrenciaDtHr() {
        return ocorrenciaDtHr;
    }

    public void setOcorrenciaDtHr(Timestamp ocorrenciaDtHr) {
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
   
}
