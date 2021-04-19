package ru.otus.library.service.data.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.library.domain.Book;
import ru.otus.library.domain.Comment;
import ru.otus.library.repository.CommentRepository;
import ru.otus.library.service.data.BookService;
import ru.otus.library.service.data.CommentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CommentServiceImpl.class)
@DisplayName(value = "CommentServiceImpl should ")
class CommentServiceImplTest {
    public static final long ID_BOOK = 1L;
    public static final String NEW_COMMENT_CAPTION = "WOW! New comment!";
    @MockBean
    private BookService bookService;
    @MockBean
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    /*@Test
    @DisplayName("add COMMENT to BOOK by BOOK ID")
    void shouldAddCommentToBook() {
        when(bookService.getById(ID_BOOK)).thenReturn(new Book());
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);

        Comment actualComment = commentService.addCommentToBook(ID_BOOK, NEW_COMMENT_CAPTION);

        assertThat(actualComment).isNotNull();
        assertThat(actualComment.getBook()).isNotNull();
        assertThat(actualComment.getCaption()).isEqualTo(NEW_COMMENT_CAPTION);
    }*/

}
