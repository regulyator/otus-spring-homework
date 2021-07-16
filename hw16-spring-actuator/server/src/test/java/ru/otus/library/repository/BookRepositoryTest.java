package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import ru.otus.library.configuration.MongoConfiguration;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should ")
@DataMongoTest
@Import(MongoConfiguration.class)
class BookRepositoryTest {

    public static final int EXPECTED_UN_UPDATED_COMMENT_SIZE = 2;
    public static final int EXPECTED_UPDATED_COMMENT_SIZE = 1;

    public static final String EXIST_BOOK_NAME = "Blindsight-test";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoOperations mongoOperations;

    @DisplayName("find book by name")
    @Test
    void shouldFindBookByName() {
        Book book = bookRepository.findByBookName(EXIST_BOOK_NAME).orElseThrow(EntityNotFoundException::new);

        assertThat(book).isNotNull().hasNoNullFieldsOrProperties();

    }

}