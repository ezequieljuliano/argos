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
package br.com.ezequieljuliano.argos.config;

import br.com.ezequieljuliano.argos.business.LoadDataBC;
import br.gov.frameworkdemoiselle.lifecycle.Startup;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.util.Beans;
import javax.inject.Inject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@BusinessController
public class AppStartup {

    @Inject
    private AppContext appContext;

    private void mongoDbConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                "br.com.ezequieljuliano.argos.config",
                "br.com.ezequieljuliano.argos.persistence",
                "br.com.ezequieljuliano.argos.pattern");
        appContext.setContext(context);
    }

    @Startup
    public void init() {
        mongoDbConfig();

        LoadDataBC loadDataBC = Beans.getReference(LoadDataBC.class);
        loadDataBC.generateData();
    }

}
