package ru.otus.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("CommentRepository should ")
@DataJpaTest
class CommentRepositoryTest {
    public static final long BOOK_ID = 1L;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("return all COMMENTS by BOOK ID")
    void shouldReturnAllBookCommentByBookId() {
        System.out.println(commentRepository.findAllByBookId(BOOK_ID));
    }

}