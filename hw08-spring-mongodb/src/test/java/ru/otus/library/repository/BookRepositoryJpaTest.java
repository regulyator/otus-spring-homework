package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Genre;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository should ")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookRepositoryJpaTest {


    public static final long EXPECTED_QUERY_COUNT = 2L;
    public static final long EXPECTED_QUERY_COUNT_ALL = 2L;
    private static final long EXIST_ID_BOOK = 1;
    private static final String EXIST_BOOK_NAME = "Blindsight test";
    private static final int EXPECTED_NUMBER_OF_BOOKS = 5;
    private static final String UPDATED_BOOK_NAME = "TEST BOOK UPDATED";
    //private static final Genre UPDATED_BOOK_GENRE = new Genre(2, "Fantasy test genre");
    private static final long EXIST_ID_AUTHOR = 1;
    private static final String EXIST_FIO_AUTHOR = "Peter Watts test author";
    private static final long EXIST_ID_GENRE = 3;
    private static final String EXIST_CAPTION_GENRE = "Sci-Fi test genre";
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    /*@DisplayName("return BOOK by ID and have no N+1")
    @Test
    void shouldReturnBookById() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        Book expectedBook = getExistBook();
        Book actualBook = bookRepository.findById(EXIST_ID_BOOK).orElse(null);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERY_COUNT);
    }

    @DisplayName("return all BOOK and have no N+1")
    @Test
    void shouldReturnAllBook() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        Collection<Book> books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(book -> !book.getBookName().equals(""))
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthors().size() > 0);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERY_COUNT_ALL);
    }

    @DisplayName("update BOOK")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = getExpectedUpdateBook();

        Book storedBook = bookRepository.findById(EXIST_ID_BOOK).orElse(null);
        assertThat(storedBook).usingRecursiveComparison().isNotEqualTo(expectedBook);
        Objects.requireNonNull(storedBook).setBookName(UPDATED_BOOK_NAME);
        storedBook.setAuthors(getUpdatedAuthors());
        storedBook.setGenre(UPDATED_BOOK_GENRE);
        bookRepository.save(storedBook);
        Book storedUpdatedBook = bookRepository.findById(EXIST_ID_BOOK).orElse(null);

        assertThat(storedUpdatedBook).isNotNull();
        assertThat(expectedBook.getId()).isEqualTo(storedUpdatedBook.getId());
        assertThat(expectedBook.getBookName()).isEqualTo(storedBook.getBookName());
        assertThat(expectedBook.getGenre()).isEqualTo(storedUpdatedBook.getGenre());
        assertThat(storedUpdatedBook.getAuthors()).hasSize(2)
                .isEqualTo(expectedBook.getAuthors());
    }

    private Book getExistBook() {
        return new Book(EXIST_ID_BOOK,
                EXIST_BOOK_NAME,
                getExistGenre(),
                Set.of(getExistAuthor())
        );
    }

    private Book getExpectedUpdateBook() {
        Book book = new Book();
        book.setId(EXIST_ID_BOOK);
        book.setBookName(UPDATED_BOOK_NAME);
        book.setGenre(UPDATED_BOOK_GENRE);
        book.setAuthors(getUpdatedAuthors());
        return book;
    }

    private Author getExistAuthor() {
        return new Author(EXIST_ID_AUTHOR, EXIST_FIO_AUTHOR);
    }

    private Genre getExistGenre() {
        return new Genre(EXIST_ID_GENRE, EXIST_CAPTION_GENRE);
    }

    private Set<Author> getUpdatedAuthors() {

        Set<Author> authors = new HashSet<>();
        authors.add(new Author(1, "Peter Watts test author"));
        authors.add(new Author(2, "Robert Hainline test author"));
        return authors;
    }*/
}