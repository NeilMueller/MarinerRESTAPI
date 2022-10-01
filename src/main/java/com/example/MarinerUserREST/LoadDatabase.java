/**
 * used to preload data for trouble shooting db
 * make sure to uncomment drop n tables in application properties if you want to use this
 * otherwise you will end up with duplicate data
 */
package com.example.MarinerUserREST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            //log.info("Preloading " + repository.save(new UserObject("Mueller", "Neil", "neil.mueller99@gmail.com", simpleDateFormat.parse("1999-11-01"),1)));
            //log.info("Preloading " + repository.save(new UserObject("Boyle", "Kyle", "kb@gmail.com", simpleDateFormat.parse("1980-01-01"),1)));
        };
    }
}
