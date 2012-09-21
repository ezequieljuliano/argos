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
import java.sql.Date;
import java.sql.Time;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Entity
@Table(name = "EVENTO", schema = "Argos@cassandra_pu")
@XmlRootElement
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "EVENTO_ID")
    private String id;
    
    @Column(name = "COMPUTADOR_GERADOR")
    private String computadorGerador;
    
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @Column(name = "ENDERECO_IP")
    private String enderecoIp;
    
    @Column(name = "FONTE")
    private String fonte;
    
    @Column(name = "NOME")
    private String nome;
    
    @Column(name = "OCORRENCIA_DATA")
    private java.sql.Date ocorrenciaData;
    
    @Column(name = "OCORRENCIA_HORA")
    private java.sql.Time ocorrenciaHora;
    
    @Column(name = "PALAVRAS_CHAVE")
    private String palavrasChave;
    
    @Column(name = "USUARIO_GERADOR")
    private String usuarioGerador;
    
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

    public String getComputadorGerador() {
        return computadorGerador;
    }

    public void setComputadorGerador(String computadorGerador) {
        this.computadorGerador = computadorGerador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEnderecoIp() {
        return enderecoIp;
    }

    public void setEnderecoIp(String enderecoIp) {
        this.enderecoIp = enderecoIp;
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

    public Date getOcorrenciaData() {
        return ocorrenciaData;
    }

    public void setOcorrenciaData(Date ocorrenciaData) {
        this.ocorrenciaData = ocorrenciaData;
    }

    public Time getOcorrenciaHora() {
        return ocorrenciaHora;
    }

    public void setOcorrenciaHora(Time ocorrenciaHora) {
        this.ocorrenciaHora = ocorrenciaHora;
    }

    public String getPalavrasChave() {
        return palavrasChave;
    }

    public void setPalavrasChave(String palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    public String getUsuarioGerador() {
        return usuarioGerador;
    }

    public void setUsuarioGerador(String usuarioGerador) {
        this.usuarioGerador = usuarioGerador;
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
