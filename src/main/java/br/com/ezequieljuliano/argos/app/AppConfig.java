package br.com.ezequieljuliano.argos.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.Mongo;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

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
        Directory directory = new RAMDirectory();
        return directory;
    }
}
