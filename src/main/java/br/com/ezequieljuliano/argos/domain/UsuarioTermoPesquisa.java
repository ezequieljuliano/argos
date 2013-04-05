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
import java.util.Objects;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class UsuarioTermoPesquisa implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private EventoTermoPesquisa termo;
    private String valor;

    public UsuarioTermoPesquisa() {
    }

    public UsuarioTermoPesquisa(EventoTermoPesquisa termo, String valor) {
        this.termo = termo;
        this.valor = valor;
    }

    public EventoTermoPesquisa getTermo() {
        return termo;
    }

    public void setTermo(EventoTermoPesquisa termo) {
        this.termo = termo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UsuarioTermoPesquisa other = (UsuarioTermoPesquisa) obj;
        if (this.termo != other.termo) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return true;
    }
   
}
