package com.ista.isp.assessment.todo;

import com.ista.isp.assessment.todo.models.TodoEntity;
import com.ista.isp.assessment.todo.repositories.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * load test data for testing, if the api can load the data in the database
 */
@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(TodoRepository repository) {
        return args -> {
//            log.info("Init data load " + repository.save(new TodoEntity("Test todo item 1", false)));
//            log.info("Init data load " + repository.save(new TodoEntity("Test todo item 2", false)));
//            log.info("Init data load " + repository.save(new TodoEntity("Test todo item 3", true)));
        };
    }
}
