package com.hello.screen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class HelloScreenApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloScreenApplication.class, args);
    }
}
