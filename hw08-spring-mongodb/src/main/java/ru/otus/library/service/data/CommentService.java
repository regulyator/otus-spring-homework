package ru.otus.library.service.data;

import ru.otus.library.domain.Comment;
import ru.otus.library.domain.dto.CommentDto;

import java.util.Collection;

public interface CommentService {

    Comment getById(String id);

    CommentDto getByIdDto(String id);

    Comment updateCommentCaption(String id, String newCommentCaption);

    Comment addCommentToBook(String idBook, String newCommentCaption);

    void removeComment(String idComment);
}
