package ru.otus.icdimporter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class PrepareForJobServiceImpl implements PrepareForJobService {
    private static final String ICDS_COLLECTION_NAME = "icds";
    private final MongoOperations mongoOperations;
    private final JdbcOperations jdbcOperations;

    @Autowired
    public PrepareForJobServiceImpl(MongoOperations mongoOperations, JdbcOperations jdbcOperations) {
        this.mongoOperations = mongoOperations;
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void prepare() {
        mongoOperations.dropCollection(ICDS_COLLECTION_NAME);
        jdbcOperations.execute("DELETE FROM ICD");
    }
}
