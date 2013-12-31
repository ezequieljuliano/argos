/*
 * Copyright 2013 Ezequiel Juliano Müller - ezequieljuliano@gmail.com.
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

import br.com.ezequieljuliano.argos.domain.Profile;
import br.com.ezequieljuliano.argos.domain.User;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import java.io.Serializable;
import javax.inject.Inject;

@ViewController
public class SecurityMB implements Serializable {

    private User user;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private MessageContext messageContext;

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String doLogin() {
        try {
            securityContext.login();
            if (!securityContext.isLoggedIn()) {
                throw new Exception("Não foi possível acessar o sistema. Seu usuário é inválido ou está INATIVO!");
            }
            return "/index.jsf";
        } catch (Exception e) {
            messageContext.add(e.getMessage(), SeverityType.ERROR);
            return "/login.jsf";
        }
    }

    public boolean userIsLogged() {
        return (securityContext.hasRole(Profile.admin.name())
                || securityContext.hasRole(Profile.analyst.name()));
    }

    public boolean userLoggedIsAdmin() {
        return securityContext.hasRole(Profile.admin.name());
    }

    public void logout() {
        securityContext.logout();
    }

}
