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
package br.com.ezequieljuliano.argos.app;

import br.com.ezequieljuliano.argos.constant.Constantes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.Mongo;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;

@Configuration
public class AppConfig {

    public @Bean
    MongoOperations mongoTemplate(Mongo mongo) {
        MongoTemplate mongoTemplate = new MongoTemplate(mongo, "Argos");
        return mongoTemplate;
    }

    /*
     * Factory bean that creates the Mongo instance
     */
    public @Bean
    MongoFactoryBean mongo() {
        MongoFactoryBean mongo = new MongoFactoryBean();
        mongo.setHost("localhost");
        return mongo;
    }

    /*
     * Use this post processor to translate any MongoExceptions thrown in @Repository annotated classes
     */
    public @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /*
     * Inicia o diretório de armazenamento dos índices do Lucene
     */
    public @Bean
    Directory getDirectory() {
        return getDirDisco(null);
    }

    /*
     * Verifica se o sistema operacional é Windows
     */
    private static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf("win") >= 0);
    }

    /*
     * Abre o diretório do disco rígido
     */
    private Directory getDirDisco(File file) {
        if (file == null) {
            file = getNewFile();
        }
        if (isWindows()) {
            try {
                return FSDirectory.open(file); // Abre Diretorio em ambientes Windows
            } catch (IOException ex) {
                throw new RuntimeException("Erro ao criar diretório do Lucene", ex);
            }
        } else {
            try {
                return NIOFSDirectory.open(file); // Abre Diretorio em ambientes Unix
            } catch (IOException ex) {
                throw new RuntimeException("Erro ao criar diretório do Lucene", ex);
            }
        }
    }

    private File getNewFile() {
        //Abre ou cria o diretório em disco rígido
        File file = new File(Constantes.getLuceneIndexDirectory());
        //Se o diretório não exitir cria ele
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }
}
