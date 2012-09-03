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

import br.com.ezequieljuliano.argos.business.UsuarioBC;
import br.com.ezequieljuliano.argos.domain.Usuario;
import br.com.ezequieljuliano.argos.view.LoginMB;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class Autenticador implements Authenticator {

    @Inject
    private LoginMB loginMB;
    @Inject
    private UsuarioBC usuarioBC;
    @Inject
    private SessionAttributes sessionAttributes;

    @Override
    public boolean authenticate() {
        Usuario retorno = usuarioBC.login(loginMB.getUsuario().getUserName(), loginMB.getUsuario().getPassword());
        if (retorno == null) {
            return false;
        }
        sessionAttributes.setUsuario(retorno);
        return true;
    }

    @Override
    public void unAuthenticate() {
        sessionAttributes = null;
    }

    @Override
    public User getUser() {
        if (sessionAttributes.getUsuario() == null) {
            return null;
        } else {
            return new User() {
                @Override
                public String getId() {
                    return "" + sessionAttributes.getUsuario().getUserName();
                }

                @Override
                public Object getAttribute(Object o) {
                    return null;
                }

                @Override
                public void setAttribute(Object o, Object o1) {
                }
            };
        }
    }
}
