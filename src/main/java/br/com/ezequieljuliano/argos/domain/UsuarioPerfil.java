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
import javax.inject.Named;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@Named
public enum UsuarioPerfil implements Serializable {

    administrador("Administrador"),
    normal("Normal");
    
    private static final long serialVersionUID = 1L;
    
    private String nome;

    private UsuarioPerfil(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
