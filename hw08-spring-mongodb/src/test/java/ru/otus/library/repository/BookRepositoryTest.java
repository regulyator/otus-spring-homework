package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.library.domain.Book;
import ru.otus.library.exception.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should ")
@DataMongoTest
class BookRepositoryTest {

    public static final int EXPECTED_UN_UPDATED_COMMENT_SIZE = 2;
    public static final int EXPECTED_UPDATED_COMMENT_SIZE = 1;

    public static final String EXIST_BOOK_NAME = "Blindsight-test";
    @Autowired
    private BookRepository bookRepository;

    @DisplayName("delete book comment by idComment")
    @Test
    void shouldDeleteBookCommentByIdComment() {
        Book unUpdatedBook = bookRepository.findByBookName(EXIST_BOOK_NAME).orElseThrow(EntityNotFoundException::new);
        String commentIdToDelete = unUpdatedBook.getComments().get(0).getId();

        assertThat(unUpdatedBook.getComments()).hasSize(EXPECTED_UN_UPDATED_COMMENT_SIZE);

        bookRepository.deleteBookComment(unUpdatedBook.getId(), commentIdToDelete);

        Book updatedBook = bookRepository.findByBookName(EXIST_BOOK_NAME).orElseThrow(EntityNotFoundException::new);

        assertThat(updatedBook.getComments()).hasSize(EXPECTED_UPDATED_COMMENT_SIZE)
                .noneMatch(comment -> comment.getId().equals(commentIdToDelete));

    }

    @DisplayName("find book by name")
    @Test
    void shouldFindBookByName() {
        Book book = bookRepository.findByBookName(EXIST_BOOK_NAME).orElseThrow(EntityNotFoundException::new);

        assertThat(book).isNotNull().hasNoNullFieldsOrProperties();

    }

}