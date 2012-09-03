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

import com.impetus.kundera.annotations.Index;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Entity
@Table(name = "EVENTO_TIPO", schema = "Argos@cassandra_pu")
@Index(index = true, columns = {"CODIGO"})
public class EventoTipo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "EVENTO_TIPO_ID")
    private String id;
    
    @Column(name = "CODIGO")
    private int codigo;
    
    @Column(name = "DESCRICAO")
    private String descricao;
    
    @Column(name = "SITUACAO")
    private Situacao situacao = Situacao.ativo;

    public EventoTipo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public boolean isAtivo() {
        return situacao.equals(Situacao.ativo);
    }

    public boolean isInativo() {
        return situacao.equals(Situacao.inativo);
    }
}