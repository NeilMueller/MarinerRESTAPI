package com.example.MarinerUserREST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new UserObject("Mueller", "Neil", "neil.mueller99@gmail.com", new Date(1999, 11, 1),1)));
            log.info("Preloading " + repository.save(new UserObject("Boyle", "Kyle", "kb@gmail.com", new Date(1980, 1, 1),1)));
        };
    }
}
