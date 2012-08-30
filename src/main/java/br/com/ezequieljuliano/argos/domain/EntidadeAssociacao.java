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
@Table(name = "ENTIDADE_ASSOCIACAO", schema = "Argos@cassandra_pu")
public class EntidadeAssociacao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "ID")
    private String id;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADE_PAI_ID")
    private Entidade entidadePai;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ENTIDADE_FILHA_ID")
    private Entidade entidadeFilha;

    public EntidadeAssociacao() {
        super();
    }

    public EntidadeAssociacao(Entidade entidadePai) {
        this.entidadePai = entidadePai;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Entidade getEntidadePai() {
        return entidadePai;
    }

    public void setEntidadePai(Entidade entidadePai) {
        this.entidadePai = entidadePai;
    }

    public Entidade getEntidadeFilha() {
        return entidadeFilha;
    }

    public void setEntidadeFilha(Entidade entidadeFilha) {
        this.entidadeFilha = entidadeFilha;
    }
    
}
