package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AuthorDaoJpa should ")
@DataJpaTest
@Import(AuthorDaoJpa.class)
@Transactional
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
    @Autowired
    private TestEntityManager testEntityManager;

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

    @DisplayName("insert new AUTHOR")
    @Test
    void shouldInsertAuthorAndGenerateId() {
        Collection<Author> oldExistingAuthors = authorDao.findAll();

        Author author = new Author();
        author.setFio(NEW_AUTHOR_FIO);
        Author actualAuthor = authorDao.save(author);

        Collection<Author> newExistingAuthors = authorDao.findAll();

        assertThat(true).isEqualTo(author.getId() > 0L);
        assertThat(oldExistingAuthors).isNotEqualTo(newExistingAuthors);
        assertThat(true).isEqualTo(newExistingAuthors.contains(actualAuthor));
    }

    @DisplayName("return AUTHOR by ID")
    @Test
    void shouldReturnAuthorById() {
        Author expectedAuthor = new Author(EXIST_ID_AUTHOR, EXIST_FIO_AUTHOR);
        Author actualAuthor = authorDao.findById(EXIST_ID_AUTHOR).orElse(null);

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("return all AUTHOR by ids")
    @Test
    void shouldReturnAllAuthorsByIds() {
        Collection<Author> expectedAuthors = getExpectedByIdsAuthors();
        Collection<Author> actualAuthors = authorDao.findAll(List.of(1L, 3L));

        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
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

        Author storedAuthor = authorDao.findById(EXIST_ID_AUTHOR).orElse(null);
        assertThat(storedAuthor).usingRecursiveComparison().isNotEqualTo(expectedAuthor);
        Objects.requireNonNull(storedAuthor).setFio(EXPECTED_UPDATED_FIO_AUTHOR);
        Author storedUpdatedAuthor = authorDao.save(storedAuthor);

        assertThat(storedUpdatedAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("delete AUTHOR by ID")
    @Test
    void shouldDeleteAuthor() {
        assertThatCode(() -> authorDao.findById(EXIST_NON_RELATED_ID_AUTHOR))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXIST_NON_RELATED_ID_AUTHOR);
        testEntityManager.clear();

        assertThat(true).isEqualTo(authorDao.findById(EXIST_NON_RELATED_ID_AUTHOR).isEmpty());
    }

    @DisplayName("throw PersistenceException when delete AUTHOR related to books")
    @Test
    void shouldThrowExceptionWhenTryDeleteAuthorRelatedToBooks() {
        assertThatCode(() -> authorDao.findById(EXIST_ID_AUTHOR))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> authorDao.deleteById(EXIST_ID_AUTHOR))
                .isInstanceOf(PersistenceException.class);

        assertThatCode(() -> authorDao.findById(EXIST_ID_AUTHOR))
                .doesNotThrowAnyException();
    }

    private List<Author> getAllExistingAuthors() {
        return List.of(
                new Author(1, "Peter Watts test author"),
                new Author(2, "Robert Hainline test author"),
                new Author(3, "Arkady and Boris Strugatsky test author"),
                new Author(4, "Vernor Vinge test author")
        );
    }

    private List<Author> getExpectedByIdsAuthors() {
        return List.of(
                new Author(1, "Peter Watts test author"),
                new Author(3, "Arkady and Boris Strugatsky test author")
        );
    }

}
