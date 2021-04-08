package ru.otus.library.service.data;

import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.CommentDto;

import java.util.Collection;

public interface CommentService {

    Comment getById(long id);

    CommentDto getByIdDto(long id);

    Comment updateCommentCaption(long id, String newCommentCaption);

    Collection<Comment> getAllBookComment(long bookId);

    Collection<CommentDto> getAllBookCommentDto(long bookId);

    Comment addCommentToBook(long idBook, String newCommentCaption);

    void removeComment(long idComment);
}
