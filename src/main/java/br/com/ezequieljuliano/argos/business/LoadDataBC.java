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
package br.com.ezequieljuliano.argos.business;

import br.com.ezequieljuliano.argos.domain.Profile;
import br.com.ezequieljuliano.argos.domain.Situation;
import br.com.ezequieljuliano.argos.domain.User;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import javax.inject.Inject;

@BusinessController
public class LoadDataBC {

    @Inject
    private UserBC userBC;

    public void generateData() {
        generateUsers();
    }

    private void generateUsers() {
        if (userBC.findOneByUserName("admin") == null) {
            User user = new User();
            user.setUserName("admin");
            user.setPassWord("admin");
            user.setEmail("ezequieljuliano@gmail.com");
            user.setSituation(Situation.active);
            user.linkProfile(Profile.admin);
            user.linkProfile(Profile.analyst);
            userBC.save(user);
        }
    }

}
