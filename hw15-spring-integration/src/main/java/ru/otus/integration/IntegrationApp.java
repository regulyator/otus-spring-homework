package ru.otus.integration;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;


@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
@EnableMongock
@EnableMongoRepositories
public class IntegrationApp {

    public static void main(String[] args) {
        SpringApplication.run(IntegrationApp.class, args);
    }
}
