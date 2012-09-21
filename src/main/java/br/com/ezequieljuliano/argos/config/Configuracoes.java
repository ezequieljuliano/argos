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
package br.com.ezequieljuliano.argos.config;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Ezequiel Juliano Müller
 */
public class Configuracoes {

    private static Properties properties;
    private static Configuracoes instance;

    private Configuracoes() {
        
    }

    private Configuracoes(Properties properties) {
        this.properties = properties;
    }

    public static Configuracoes load() throws Exception {
        try {
            properties = new Properties();
            properties.load(Configuracoes.class.getClassLoader().getResourceAsStream("argos.properties"));
            instance = new Configuracoes(properties);
        } catch (IOException ex) {
            throw new Exception("Não foi possível carregar as configurações do arquivo argos.properties", ex);
        }
        return instance;
    }

    public String getLuceneIndices() {
        return properties.getProperty("lucene.indices");
    }
}
