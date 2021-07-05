package ru.otus.library.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.library.config.TestAclMethodSecurityConfiguration;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;
import ru.otus.library.domain.dto.BookDto;
import ru.otus.library.repository.AuthorRepository;
import ru.otus.library.repository.BookRepository;
import ru.otus.library.repository.GenreRepository;
import ru.otus.library.service.data.AuthorService;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.GenreService;
import ru.otus.library.service.data.impl.AuthorServiceImpl;
import ru.otus.library.service.data.impl.BookServiceImpl;
import ru.otus.library.service.data.impl.GenreServiceImpl;
import ru.otus.library.service.security.AclPermissionGrant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(TestAclMethodSecurityConfiguration.class)
@SpringBootTest(classes = {BookServiceImpl.class,
        AuthorServiceImpl.class,
        GenreServiceImpl.class})
@DisplayName("security application should")
class SecurityIntegrationTest {
    private static final Book BOOK = new Book(null,
            "BOOK_NAME",
            new Genre("GENRE_ID", "GENRE_CAPTION"),
            Collections.emptyList(),
            Collections.emptyList());
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private AclPermissionGrant aclPermissionGrant;
    @MockBean
    private PermissionEvaluator testPermissionEvaluator;


    @DisplayName("throws AccessDeniedException when save book without role ADMIN")
    @Test
    @WithMockUser(username = "user")
    void shouldThrowAccessDeniedExceptionWhenSaveBookWithoutRoleAdmin() {
        assertThrows(AccessDeniedException.class, () -> bookService.createOrUpdate(new BookDto(BOOK)));
    }

    @DisplayName("not throws AccessDeniedException when save book with role ADMIN")
    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void shouldNotThrowAccessDeniedExceptionWhenSaveBookWithRoleAdmin() {
        when(bookRepository.save(any())).thenReturn(BOOK);
        assertDoesNotThrow(() -> bookService.createOrUpdate(new BookDto(BOOK)));
    }

    @DisplayName("return empty genre list without permission and roles")
    @Test
    @WithMockUser(username = "user")
    void shouldReturnEmptyGenreListWithoutPermissionAndRoles() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(false);
        when(genreService.getAll()).thenReturn(genres);
        assertThat(genreService.getAll()).isEmpty();
    }

    @DisplayName("return not empty genre list with role admin")
    @Test
    @WithMockUser(value = "user", roles = {"ADMIN"})
    void shouldReturnNotEmptyGenreListWithRole() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(false);
        when(genreService.getAll()).thenReturn(genres);
        assertThat(genreService.getAll()).isNotEmpty();
    }

    @DisplayName("return not empty genre list with role and permission")
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldReturnNotEmptyGenreListWithRoleAndPermission() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(true);
        when(genreService.getAll()).thenReturn(genres);
        assertThat(genreService.getAll()).isNotEmpty();
    }

    @DisplayName("filter genres by permission")
    @Test
    @WithMockUser(username = "admin")
    void shouldFilterGenresByPermission() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre("1", "TEST"));
        genres.add(new Genre("2", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(true)
                .thenReturn(false);
        when(genreService.getAll()).thenReturn(genres);
        assertThat(genreService.getAll()).hasSize(1)
                .allMatch(genre -> genre.getId().equals("1"));
    }

    @DisplayName("return empty author list without permission and roles")
    @Test
    @WithMockUser(username = "user")
    void shouldReturnEmptyAuthorListWithoutPermissionAndRoles() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(false);
        when(authorService.getAll()).thenReturn(authors);
        assertThat(authorService.getAll()).isEmpty();
    }

    @DisplayName("return not empty author list with role admin")
    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void shouldReturnNotEmptyAuthorListWithRole() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(false);
        when(authorService.getAll()).thenReturn(authors);
        assertThat(authorService.getAll()).isNotEmpty();
    }

    @DisplayName("return not empty author list with role and permission")
    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void shouldReturnNotEmptyAuthorListWithRoleAndPermission() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(true);
        when(authorService.getAll()).thenReturn(authors);
        assertThat(authorService.getAll()).isNotEmpty();
    }

    @DisplayName("filter authors by permission")
    @Test
    @WithMockUser(username = "admin")
    void shouldFilterAuthorsByPermission() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author("1", "TEST"));
        authors.add(new Author("2", "TEST"));
        when(testPermissionEvaluator.hasPermission(any(), any(), any())).thenReturn(true)
                .thenReturn(false);
        when(authorService.getAll()).thenReturn(authors);
        assertThat(authorService.getAll()).hasSize(1)
                .allMatch(author -> author.getId().equals("1"));
    }


}
