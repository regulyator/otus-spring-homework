package ru.otus.library.configuration;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongock
@EnableMongoRepositories(basePackages = "ru.otus.library.repository")
public class MongoConfiguration {
}
