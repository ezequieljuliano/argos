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
package br.com.ezequieljuliano.argos.security;

import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.domain.UsuarioPerfil;
import br.com.ezequieljuliano.argos.exception.AcessoNegadoException;
import br.gov.frameworkdemoiselle.security.Authorizer;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class Autorizador implements Authorizer {

    @Inject
    private SessionAttributes sessionAttributes;

    @Override
    public boolean hasRole(String requiredRole) {
        Usuario usuarioLogado = sessionAttributes.getUsuario();
        UsuarioPerfil perfil = usuarioLogado.getPerfil();
        Boolean autorizado = false;
        if (perfil.equals(UsuarioPerfil.administrador)) {
            autorizado = true;
        } else if (getUsuarioPerfil(requiredRole).equals(UsuarioPerfil.normal) && perfil.equals(UsuarioPerfil.normal)) {
            autorizado = true;
        }
        if (requiredRole.startsWith("@") && autorizado == false) {
            throw new AcessoNegadoException("Você não está autorizado a acessar este recurso!");
        }
        return autorizado;
    }

    @Override
    public boolean hasPermission(String string, String string1) {
        return false;
    }

    private UsuarioPerfil getUsuarioPerfil(String requiredRole) {
        if (requiredRole.equals("A") || requiredRole.equals("@A")) {
            return UsuarioPerfil.administrador;
        } else {
            return UsuarioPerfil.normal;
        }
    }
}
