package ru.otus.library.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import ru.otus.library.domain.Comment;

@AllArgsConstructor
@ToString
public class CommentDto {
    private final String id;
    private final String caption;

    public CommentDto(@NonNull Comment comment) {
        this.id = comment.getId();
        this.caption = comment.getCaption();
    }
}
