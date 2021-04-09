package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.library.dao.CommentDao;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.domain.Genre;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("CommentDaoJpa should ")
@DataJpaTest
@Import(CommentDaoJpa.class)
class CommentDaoJpaTest {

    public static final long EXIST_COMMENT_ID = 1L;
    public static final long NON_EXIST_COMMENT_ID = 20L;
    public static final String UPDATED_COMMENT_CAPTION = "UPDATED COMMENT CAPTION";
    public static final String EXIST_COMMENT_CAPTION = "nice book!";
    public static final String NEW_COMMENT_CAPTION = "NEW COMMENT";


    @Autowired
    private CommentDao commentDao;
    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("return true if COMMENT id exist")
    @Test
    void shouldReturnTrueIfCommentIdExist() {
        assertThat(true).isEqualTo(commentDao.isExistById(EXIST_COMMENT_ID));
    }

    @DisplayName("return false if COMMENT id not exist")
    @Test
    void shouldReturnTrueIfCommentIdNotExist() {
        assertThat(false).isEqualTo(commentDao.isExistById(NON_EXIST_COMMENT_ID));
    }

    @DisplayName("insert COMMENT")
    @Test
    void shouldInsertCommentAndGenerateId() {
        Collection<Comment> oldExistingComments = commentDao.findAll();

        Comment comment = new Comment();
        comment.setId(0L);
        comment.setCaption(NEW_COMMENT_CAPTION);
        comment.setBook(getExistBook());

        commentDao.save(comment);

        Collection<Comment> newExistingComments = commentDao.findAll();

        assertThat(true).isEqualTo(comment.getId() > 0L);
        assertThat(oldExistingComments).isNotEqualTo(newExistingComments);
        assertThat(true).isEqualTo(newExistingComments.contains(comment));
    }

    @DisplayName("return COMMENT by ID")
    @Test
    void shouldReturnCommentById() {
        Comment actualComment = commentDao.findById(EXIST_COMMENT_ID).orElse(null);

        assertThat(actualComment).isNotNull()
                .returns(getExistComment().getBook(), Comment::getBook)
                .returns(getExistComment().getId(), Comment::getId)
                .returns(getExistComment().getCaption(), Comment::getCaption);
    }

    @DisplayName("return all COMMENT")
    @Test
    void shouldReturnAllComment() {
        Collection<Comment> actualComments = commentDao.findAll();

        assertThat(actualComments).isNotNull().hasSize(9)
                .allMatch(comment -> !comment.getCaption().equals(""))
                .allMatch(comment -> comment.getBook() != null);
    }

    @DisplayName("return all COMMENT by BOOK ID")
    @Test
    void shouldReturnAllCommentByBookId() {
        Collection<Comment> actualComment = commentDao.findAllByBookId(1L);

        assertThat(actualComment).isNotNull().hasSize(3)
                .allMatch(comment -> comment.getBook().equals(getExistBook()))
                .allMatch(comment -> !comment.getCaption().isEmpty());
    }

    @DisplayName("update COMMENT")
    @Test
    void shouldUpdateComment() {
        Comment expectedComment = getUpdatedComment();
        Comment storedComment = commentDao.findById(EXIST_COMMENT_ID).orElse(null);

        assertThat(storedComment).usingRecursiveComparison().isNotEqualTo(expectedComment);
        Objects.requireNonNull(storedComment).setCaption(UPDATED_COMMENT_CAPTION);
        storedComment = commentDao.save(storedComment);

        assertThat(storedComment).isNotNull()
                .returns(expectedComment.getBook(), Comment::getBook)
                .returns(expectedComment.getId(), Comment::getId)
                .returns(expectedComment.getCaption(), Comment::getCaption);
    }

    @DisplayName("delete COMMENT by ID")
    @Test
    void shouldDeleteComment() {

        assertThatCode(() -> commentDao.findById(EXIST_COMMENT_ID))
                .doesNotThrowAnyException();

        commentDao.deleteById(EXIST_COMMENT_ID);
        testEntityManager.clear();

        assertThat(true).isEqualTo(commentDao.findById(EXIST_COMMENT_ID).isEmpty());
    }


    private Comment getExistComment() {
        return new Comment(EXIST_COMMENT_ID, EXIST_COMMENT_CAPTION, getExistBook());
    }

    private Comment getUpdatedComment() {
        return new Comment(EXIST_COMMENT_ID, UPDATED_COMMENT_CAPTION, getExistBook());
    }

    private Book getExistBook() {
        return new Book(1L,
                "Blindsight test",
                new Genre(3L, "Sci-Fi test genre"),
                Set.of(new Author(1L, "Peter Watts test author"))
        );
    }

}