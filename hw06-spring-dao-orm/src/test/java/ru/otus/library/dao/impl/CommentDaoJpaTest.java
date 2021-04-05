package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.dao.CommentDao;
import ru.otus.library.domain.Comment;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("CommentDaoJpa should ")
@DataJpaTest
@Import(CommentDaoJpa.class)
@Transactional
class CommentDaoJpaTest {

    public static final long EXIST_COMMENT_ID = 1L;
    public static final long NON_EXIST_COMMENT_ID = 20L;
    public static final Comment EXIST_COMMENT = new Comment(1L, "nice book!");
    public static final String UPDATED_COMMENT_CAPTION = "UPDATED COMMENT CAPTION";
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

    @DisplayName("return COMMENT by ID")
    @Test
    void shouldReturnCommentById() {
        Comment actualComment = commentDao.findById(EXIST_COMMENT_ID).orElse(null);

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(EXIST_COMMENT);
    }

    @DisplayName("return all COMMENT")
    @Test
    void shouldReturnAllComment() {
        Collection<Comment> expectedComments = getAllExistingComments();
        Collection<Comment> actualComments = commentDao.findAll();

        assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
    }

    @DisplayName("update COMMENT")
    @Test
    void shouldUpdateComment() {
        Comment expectedComment = new Comment(EXIST_COMMENT_ID, UPDATED_COMMENT_CAPTION);
        Comment storedComment = commentDao.findById(EXIST_COMMENT_ID).orElse(null);

        assertThat(storedComment).usingRecursiveComparison().isNotEqualTo(expectedComment);
        Objects.requireNonNull(storedComment).setCaption(UPDATED_COMMENT_CAPTION);
        storedComment = commentDao.save(storedComment);
        assertThat(storedComment).usingRecursiveComparison().isEqualTo(expectedComment);
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

    private Collection<Comment> getAllExistingComments() {
        return List.of(
                new Comment(1, "nice book!"),
                new Comment(2, "So complicated"),
                new Comment(3, "atata"),
                new Comment(4, "Mooooooon!"),
                new Comment(5, "What a brilliant book!"),
                new Comment(6, "ohhhhh"),
                new Comment(7, "Not so bad"),
                new Comment(8, "Another comment"),
                new Comment(9, "And more comment")
        );
    }

}