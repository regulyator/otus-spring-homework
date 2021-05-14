package ru.otus.library.service.data.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import ru.otus.library.domain.Author;
import ru.otus.library.exception.EntityNotFoundException;
import ru.otus.library.exception.ReferenceEntityException;
import ru.otus.library.repository.AbstractRepositoryTest;
import ru.otus.library.repository.AuthorRepository;

import static org.assertj.core.api.Assertions.*;

@DisplayName("AuthorCascadeEventListener should ")
@Import(AuthorCascadeEventListener.class)
class AuthorCascadeEventListenerTest extends AbstractRepositoryTest {
    private static final String REFERENCE_AUTHOR_FIO = "Peter Watts";
    private static final String NON_REFERENCE_AUTHOR_FIO = "Vernor Vinge";

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("not delete Author if it has references in any Book and throw Exception")
    @Test
    public void shouldNotDeleteAuthorIfItHasReferenceToAnyBookAndThrowException() {
        Author author = authorRepository.findAll().stream()
                .filter(a -> a.getFio().equals(REFERENCE_AUTHOR_FIO)).findFirst().orElseThrow(EntityNotFoundException::new);

        assertThatThrownBy(() -> authorRepository.delete(author))
                .isInstanceOf(ReferenceEntityException.class);

        assertThat(authorRepository.findAll())
                .contains(author);
    }

    @DisplayName("delete Author if it has no references in any Book")
    @Test
    public void shouldDeleteAuthorIfItHasNoReferenceToAnyBook() {
        Author author = authorRepository.findAll().stream()
                .filter(a -> a.getFio().equals(NON_REFERENCE_AUTHOR_FIO)).findFirst().orElseThrow(EntityNotFoundException::new);

        assertThatCode(() -> authorRepository.delete(author))
                .doesNotThrowAnyException();

        assertThat(authorRepository.findAll())
                .doesNotContain(author);
    }

}