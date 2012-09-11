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
package br.com.ezequieljuliano.argos.view;

import br.com.ezequieljuliano.argos.domain.Usuario;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import javax.inject.Inject;

/**
 *
 * @author Ezequiel Juliano Müller
 */
@ViewController
public class LoginMB {

    private Usuario usuario;
    @Inject
    private MessageContext messageContext;
    @Inject
    private SecurityContext securityContext;

    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public String login() {
        try {
            securityContext.login();
            if (!securityContext.isLoggedIn()) {
                throw new Exception("Não foi possível acessar o sistema. Seu usuário é inválido ou está INATIVO");
            }
            return "/index.jsf";
        } catch (Exception e) {
            messageContext.add(e.getMessage(), SeverityType.ERROR);
            return "/login.jsf";
        }
    }
}
