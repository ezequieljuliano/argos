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
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Entity
@Table(name = "Entidade", schema = "Argos@cassandra_pu")
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "Id")
    private Long id;
    
    @Column(name = "Codigo")
    private int codigo;
    
    @Column(name = "Nome")
    private String nome;
    
    @Column(name = "CadastroNacional")
    private String cadastroNacional;
    
    @Column(name = "Situacao")
    private Situacao situacao = Situacao.ativo;
    
    @Column(name = "Endereco")
    private String endereco;
    
    @Column(name = "EnderecoNumero")
    private String enderecoNumero;
    
    @Column(name = "Bairro")
    private String bairro;
    
    @Column(name = "Cidade")
    private String cidade;
    
    @Column(name = "Estado")
    private String estado;
    
    @Column(name = "Pais")
    private String pais;
    
    @Column(name = "Cep")
    private String cep;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER) 
    @JoinColumn(name = "EntidadeFilhaId")
    private List<Entidade> entidadesFilhas;
          
    public Entidade() {
        super();
    }

    public boolean isAtivo() {
        return situacao.equals(Situacao.ativo);
    }

    public boolean isInativo() {
        return situacao.equals(Situacao.inativo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCadastroNacional() {
        return cadastroNacional;
    }

    public void setCadastroNacional(String cadastroNacional) {
        this.cadastroNacional = cadastroNacional;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public List<Entidade> getEntidadesFilhas() {
        return entidadesFilhas;
    }
 
    public void addEntidadeFilha(Entidade entidadeFilha) {
        if (this.entidadesFilhas == null || this.entidadesFilhas.isEmpty()) {
            this.entidadesFilhas = new ArrayList<Entidade>();
        }
        this.entidadesFilhas.add(entidadeFilha);
    }
}
