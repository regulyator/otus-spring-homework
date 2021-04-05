package ru.otus.library.dao.impl;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.dao.BookDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("BookDaoJpa should ")
@DataJpaTest
@Import(BookDaoJpa.class)
class BookDaoJpaTest {


    public static final long EXPECTED_QUERY_COUNT = 2L;
    private static final long EXIST_ID_BOOK = 1;
    private static final long EMPTY_ID_BOOK = 0L;
    private static final String NEW_BOOK_NAME = "New book test";
    private static final String EXIST_BOOK_NAME = "Blindsight test";
    private static final int EXPECTED_NUMBER_OF_BOOKS = 5;
    private static final String UPDATED_BOOK_NAME = "TEST BOOK UPDATED";
    private static final Genre UPDATED_BOOK_GENRE = new Genre(2, "Fantasy test genre");
    private static final long EXIST_ID_AUTHOR = 1;
    private static final String EXIST_FIO_AUTHOR = "Peter Watts test author";
    private static final long EXIST_ID_GENRE = 3;
    private static final String EXIST_CAPTION_GENRE = "Sci-Fi test genre";
    @Autowired
    private BookDao bookDao;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("return true if BOOK id exist")
    @Test
    void shouldReturnTrueIfBookIdExist() {
        assertThat(true).isEqualTo(bookDao.isExistById(EXIST_ID_BOOK));
    }

    @DisplayName("return false if BOOK id not exist")
    @Test
    void shouldReturnTrueIfBookIdNotExist() {
        assertThat(false).isEqualTo(bookDao.isExistById(EMPTY_ID_BOOK));
    }

    @DisplayName("insert BOOK with generated ID")
    @Test
    void shouldInsertBookAndGenerateId() {
        Collection<Book> oldExistingBooks = bookDao.findAll();
        Book book = getNewBook();
        bookDao.save(book);

        Collection<Book> newExistingBooks = bookDao.findAll();

        assertThat(true).isEqualTo(book.getId() > 0L);
        assertThat(oldExistingBooks).isNotEqualTo(newExistingBooks);
        assertThat(true).isEqualTo(newExistingBooks.contains(book));
    }

    @DisplayName("return BOOK by ID")
    @Test
    void shouldReturnBookById() {
        Book expectedBook = getExistBook();
        Book actualBook = bookDao.findById(EXIST_ID_BOOK).orElse(null);

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("return all BOOK")
    @Test
    void shouldReturnAllBook() {
        SessionFactory sessionFactory = testEntityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        Collection<Book> books = bookDao.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(book -> !book.getBookName().equals(""))
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthors().size() > 0)
                .allMatch(book -> book.getComments().size() > 0);
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERY_COUNT);
    }

    @DisplayName("update BOOK")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = getExpectedUpdateBook();

        Book storedBook = bookDao.findById(EXIST_ID_BOOK).orElse(null);
        assertThat(storedBook).usingRecursiveComparison().isNotEqualTo(expectedBook);
        Objects.requireNonNull(storedBook).setBookName(UPDATED_BOOK_NAME);
        storedBook.setAuthors(getUpdatedAuthors());
        storedBook.setGenre(UPDATED_BOOK_GENRE);
        bookDao.save(storedBook);
        Book storedUpdatedBook = bookDao.findById(EXIST_ID_BOOK).orElse(null);

        assertThat(storedUpdatedBook).isNotNull();
        assertThat(expectedBook.getId()).isEqualTo(storedUpdatedBook.getId());
        assertThat(expectedBook.getBookName()).isEqualTo(storedBook.getBookName());
        assertThat(expectedBook.getGenre()).isEqualTo(storedUpdatedBook.getGenre());
        assertThat(storedUpdatedBook.getAuthors()).hasSize(2)
                .isEqualTo(expectedBook.getAuthors());
    }

    @DisplayName("add new comment to BOOK")
    @Test
    void shouldAddNewCommentToBook() {
        Comment newComment = new Comment(0L, "NEW COMMENT");
        Book expectedBook = getExistBook();
        expectedBook.getComments().add(newComment);

        Book storedBook = bookDao.findById(EXIST_ID_BOOK).orElse(null);
        Objects.requireNonNull(storedBook).getComments().add(newComment);
        bookDao.save(storedBook);
        Book storedUpdatedBook = bookDao.findById(EXIST_ID_BOOK).orElse(null);

        assertThat(storedUpdatedBook).isNotNull();
        assertThat(storedUpdatedBook.getComments()).hasSize(4)
                .anyMatch(comment -> comment.getCaption().equals(newComment.getCaption()));
    }

    @DisplayName("delete comment from BOOK")
    @Test
    void shouldDeleteCommentFromBook() {
        Comment removedComment = new Comment(3, "atata");
        Book expectedBook = getExistBook();
        expectedBook.getComments().remove(removedComment);

        Book storedBook = bookDao.findById(EXIST_ID_BOOK).orElse(null);
        Objects.requireNonNull(storedBook).getComments().remove(removedComment);
        bookDao.save(storedBook);
        Book storedUpdatedBook = bookDao.findById(EXIST_ID_BOOK).orElse(null);

        assertThat(storedUpdatedBook).isNotNull();
        assertThat(storedUpdatedBook.getComments()).hasSize(2)
                .noneMatch(comment -> comment.equals(removedComment));
    }


    @DisplayName("delete BOOK by ID")
    @Test
    void shouldDeleteBook() {
        assertThatCode(() -> bookDao.findById(EXIST_ID_BOOK))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXIST_ID_BOOK);
        testEntityManager.clear();

        assertThat(true).isEqualTo(bookDao.findById(EXIST_ID_BOOK).isEmpty());
    }

    private Book getNewBook() {
        Book book = new Book();
        book.setBookName(NEW_BOOK_NAME);
        book.setGenre(getExistGenre());
        book.getAuthors().addAll(Set.of(getExistAuthor()));
        return book;
    }

    private Book getExistBook() {
        return new Book(EXIST_ID_BOOK,
                EXIST_BOOK_NAME,
                getExistGenre(),
                Set.of(getExistAuthor()),
                getExistComments()
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


    private Set<Comment> getExistComments() {
        Set<Comment> comments = new HashSet<>();
        comments.add(new Comment(1, "nice book!"));
        comments.add(new Comment(2, "So complicated"));
        comments.add(new Comment(3, "atata"));
        return comments;
    }

    private Set<Author> getUpdatedAuthors() {

        Set<Author> authors = new HashSet<>();
        authors.add(new Author(1, "Peter Watts test author"));
        authors.add(new Author(2, "Robert Hainline test author"));
        return authors;
    }
}