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
package br.com.ezequieljuliano.argos.security;

import br.com.ezequieljuliano.argos.business.UserBC;
import br.com.ezequieljuliano.argos.domain.Profile;
import br.com.ezequieljuliano.argos.view.SecurityMB;
import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;
import javax.inject.Inject;

public class AppAuthenticator implements Authenticator {

    @Inject
    private AppCredential credential;

    @Inject
    private UserBC userBC;

    @Inject
    private SecurityMB securityMB;

    @Override
    public void authenticate() throws Exception {
        br.com.ezequieljuliano.argos.domain.User user = userBC.findByUserNameAndPassWord(securityMB.getUser().getUserName(), securityMB.getUser().getPassWord());
        if (user == null) {
            throw new SecurityException("Verifique suas crdenciais de acesso! Não foi possível fazer o login no sistema.");
        }
        if (!user.isActive()) {
            throw new SecurityException("Este usuário está inativo!");
        }
        if ((user.haveProfile(Profile.admin)) || (user.haveProfile(Profile.analyst))) {
            credential.setUser(user);
        } else {
            throw new SecurityException("Este usuário não possúi permissões de acesso ao sistema!");
        }
    }

    @Override
    public void unauthenticate() throws Exception {
        credential = null;
    }

    @Override
    public User getUser() {
        if (credential.getUser() == null) {
            return null;
        }
        return new User() {
            @Override
            public String getId() {
                return credential.getUser().getUserName();
            }

            @Override
            public Object getAttribute(Object key) {
                return credential.getUser();
            }

            @Override
            public void setAttribute(Object key, Object value) {

            }
        };
    }

}
