package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.domain.Author;
import ru.otus.library.repository.AuthorRepository;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AuthorDaoJpa should ")
@DataJpaTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("return all AUTHOR by ids")
    @Test
    void shouldReturnAllAuthorsByIds() {
        Collection<Author> expectedAuthors = getExpectedByIdsAuthors();
        Collection<Author> actualAuthors = authorRepository.findAllByIdIn(List.of(1L, 3L));

        assertThat(actualAuthors).usingRecursiveComparison().isEqualTo(expectedAuthors);
    }

    private List<Author> getExpectedByIdsAuthors() {
        return List.of(
                new Author(1, "Peter Watts test author"),
                new Author(3, "Arkady and Boris Strugatsky test author")
        );
    }

}
