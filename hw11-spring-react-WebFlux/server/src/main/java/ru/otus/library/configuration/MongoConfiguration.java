package ru.otus.library.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.library.repository")
public class MongoConfiguration {
}
