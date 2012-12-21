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
package br.com.ezequieljuliano.argos.service;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public enum EventoLogExceptionTipo {

    logExUsuarioInvalido(1, "Usuário Inválido"),
    logExEventoNivelInvalido(2, "Evento Nível Inválido"),
    logExEventoTipoInvalido(3, "Evento Tipo Inválido"),
    logExAoInserirLog(4, "Erro ao Inserir Log"),
    logExUsuarioSemEntidade(5, "Usuário sem Entidade Vinculada"),
    logExUsuarioInativo(6, "Usuário está Inativo"),
    logExAoInserirCampoCustomizado(7, "Erro ao Inserir Campos Customizados"), 
    logExEntidadeInativa(8, "Entidade está Inativa"), 
    logExEventoNivelInativo(9, "Evento Nível está Inativo"), 
    logExEventoTipoInativo(10, "Evento Tipo está Inativo");
    
    private Integer codigo;
    private String descricao;

    private EventoLogExceptionTipo(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }
}
