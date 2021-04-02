package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.DaoInsertNonEmptyIdException;
import ru.otus.library.exception.DaoUpdateEmptyIdException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AuthorDaoJdbc should ")
@DataJpaTest
@Import(AuthorDaoJpa.class)
class AuthorDaoJpaTest {

    private static final long EXIST_ID_AUTHOR = 1;
    private static final long EXIST_NON_RELATED_ID_AUTHOR = 4;
    private static final long NON_EXIST_ID_AUTHOR = 8;
    private static final long EMPTY_ID_AUTHOR = 0L;
    private static final String EXIST_FIO_AUTHOR = "Peter Watts test author";
    private static final String EXPECTED_UPDATED_FIO_AUTHOR = "Peter Watts test author UPDATED";
    private static final String NEW_AUTHOR_FIO = "New author test";

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("return true if AUTHOR id exist")
    @Test
    void shouldReturnTrueIfAuthorIdExist() {
        assertThat(true).isEqualTo(authorDao.isExistById(EXIST_ID_AUTHOR));
    }

    @DisplayName("return false if AUTHOR id not exist")
    @Test
    void shouldReturnTrueIfAuthorIdNotExist() {
        assertThat(false).isEqualTo(authorDao.isExistById(NON_EXIST_ID_AUTHOR));
    }

    @DisplayName("insert AUTHOR with generated ID")
    @Test
    void shouldInsertAuthorAndGenerateId() {
        Author expectedAuthor = new Author(EMPTY_ID_AUTHOR, NEW_AUTHOR_FIO);
        long generatedId = authorDao.insert(expectedAuthor);
        Author actualAuthor = authorDao.findById(generatedId);

        assertThat(generatedId).isNotEqualTo(expectedAuthor.getId());
        assertThat(actualAuthor.getFio()).isEqualTo(expectedAuthor.getFio());
    }

    @DisplayName("return AUTHOR by ID")
    @Test
    void shouldReturnAuthorById() {
        Author expectedAuthor = new Author(EXIST_ID_AUTHOR, EXIST_FIO_AUTHOR);
        Author actualAuthor = authorDao.findById(EXIST_ID_AUTHOR);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("return all AUTHOR")
    @Test
    void shouldReturnAllAuthor() {
        Collection<Author> expectedAuthors = getAllExistingAuthors();
        Collection<Author> actualAuthors = authorDao.findAll();

        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @DisplayName("update AUTHOR")
    @Test
    void shouldUpdateAuthor() {
        Author expectedAuthor = new Author(EXIST_ID_AUTHOR, EXPECTED_UPDATED_FIO_AUTHOR);

        Author storedAuthor = authorDao.findById(EXIST_ID_AUTHOR);
        assertThat(storedAuthor).usingRecursiveComparison().isNotEqualTo(expectedAuthor);
        storedAuthor.setFio(EXPECTED_UPDATED_FIO_AUTHOR);
        authorDao.update(storedAuthor);
        Author storedUpdatedAuthor = authorDao.findById(EXIST_ID_AUTHOR);

        assertThat(storedUpdatedAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("delete AUTHOR by ID")
    @Test
    void shouldDeleteAuthor() {
        assertThatCode(() -> authorDao.findById(EXIST_NON_RELATED_ID_AUTHOR))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXIST_NON_RELATED_ID_AUTHOR);

        assertThatThrownBy(() -> authorDao.findById(EXIST_NON_RELATED_ID_AUTHOR))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("throw DataIntegrityViolationException when delete AUTHOR related to books")
    @Test
    void shouldThrowExceptionWhenTryDeleteAuthorRelatedToBooks() {
        assertThatCode(() -> authorDao.findById(EXIST_ID_AUTHOR))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> authorDao.deleteById(EXIST_ID_AUTHOR))
                .isInstanceOf(DataIntegrityViolationException.class);

        assertThatCode(() -> authorDao.findById(EXIST_ID_AUTHOR))
                .doesNotThrowAnyException();
    }

    @DisplayName("throw DaoInsertNonEmptyIdException when insert AUTHOR with non 0L ID")
    @Test
    void shouldThrowExceptionWhenTryInsertWithNonEmptyId() {
        Author insertedAuthor = new Author(NON_EXIST_ID_AUTHOR, EXIST_FIO_AUTHOR);
        Collection<Author> expectedAuthors = getAllExistingAuthors();

        assertThatThrownBy(() -> authorDao.insert(insertedAuthor))
                .isInstanceOf(DaoInsertNonEmptyIdException.class);
        Collection<Author> actualAuthors = authorDao.findAll();

        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    @DisplayName("throw DaoUpdateEmptyIdException when update AUTHOR with 0L ID")
    @Test
    void shouldThrowExceptionWhenTryUpdateWithEmptyId() {
        Author updatedAuthor = new Author(EMPTY_ID_AUTHOR, EXPECTED_UPDATED_FIO_AUTHOR);
        Collection<Author> expectedAuthors = getAllExistingAuthors();

        assertThatThrownBy(() -> authorDao.update(updatedAuthor))
                .isInstanceOf(DaoUpdateEmptyIdException.class);
        Collection<Author> actualAuthors = authorDao.findAll();

        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    private List<Author> getAllExistingAuthors() {
        return List.of(
                new Author(1, "Peter Watts test author"),
                new Author(2, "Robert Hainline test author"),
                new Author(3, "Arkady and Boris Strugatsky test author"),
                new Author(4, "Vernor Vinge test author")
        );
    }

}
