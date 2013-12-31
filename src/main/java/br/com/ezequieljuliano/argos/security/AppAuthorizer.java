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

import br.com.ezequieljuliano.argos.domain.Profile;
import br.gov.frameworkdemoiselle.security.Authorizer;
import javax.inject.Inject;

public class AppAuthorizer implements Authorizer {

    @Inject
    private AppCredential credential;

    @Override
    public boolean hasRole(String role) throws Exception {
        Profile profile = Profile.valueOf(role);
        if (profile == null) {
            return false;
        }
        return credential.getUser().haveProfile(profile);
    }

    @Override
    public boolean hasPermission(String resource, String operation) throws Exception {
        return false;
    }

}
