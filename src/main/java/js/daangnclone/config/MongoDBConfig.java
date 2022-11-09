package js.daangnclone.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;


@Configuration
public class MongoDBConfig extends AbstractReactiveMongoConfiguration{

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    protected String getDatabaseName() {
        return "chatdb";
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
    }

}
