package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.EntityNotFoundException;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should ")
@DataMongoTest
class BookRepositoryTest {

    public static final int EXPECTED_UN_UPDATED_COMMENT_SIZE = 2;
    public static final int EXPECTED_UPDATED_COMMENT_SIZE = 1;

    public static final String EXIST_BOOK_NAME = "Blindsight-test";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoOperations mongoOperations;

    @DisplayName("delete book comment by idComment")
    @Test
    void shouldDeleteBookCommentByIdComment() {
        Book unUpdatedBook = mongoOperations.findOne(Query.query(Criteria.where("book_name").is(EXIST_BOOK_NAME)), Book.class);
        String commentIdToDelete = Objects.requireNonNull(unUpdatedBook).getComments().get(0).getId();

        assertThat(unUpdatedBook.getComments()).hasSize(EXPECTED_UN_UPDATED_COMMENT_SIZE);

        bookRepository.deleteBookComment(unUpdatedBook.getId(), commentIdToDelete);

        Book updatedBook = mongoOperations.findOne(Query.query(Criteria.where("book_name").is(EXIST_BOOK_NAME)), Book.class);

        assertThat(Objects.requireNonNull(updatedBook).getComments())
                .hasSize(EXPECTED_UPDATED_COMMENT_SIZE)
                .noneMatch(comment -> comment.getId().equals(commentIdToDelete));

    }

    @DisplayName("find book by name")
    @Test
    void shouldFindBookByName() {
        Book book = bookRepository.findByBookName(EXIST_BOOK_NAME).orElseThrow(EntityNotFoundException::new);

        assertThat(book).isNotNull().hasNoNullFieldsOrProperties();

    }

}