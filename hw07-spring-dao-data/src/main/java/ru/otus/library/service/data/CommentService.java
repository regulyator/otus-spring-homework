package ru.otus.library.service.data;

import ru.otus.library.domain.Comment;

public interface CommentService {

    Comment getById(long id);

    Comment updateCommentCaption(long id, String newCommentCaption);
}
