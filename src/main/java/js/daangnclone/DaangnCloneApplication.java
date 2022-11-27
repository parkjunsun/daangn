package js.daangnclone;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@EnableJpaAuditing
@SpringBootApplication
public class DaangnCloneApplication {

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DaangnCloneApplication.class, args);
    }

//    @Bean
//    public ApplicationRunner applicationRunner() {
//        return args -> {
//            reactiveMongoTemplate.dropCollection(Message.class).then(reactiveMongoTemplate.createCollection(
//                    Message.class, CollectionOptions.empty().capped().size(5242880)
//            )).block();
//        };
//    }

}
