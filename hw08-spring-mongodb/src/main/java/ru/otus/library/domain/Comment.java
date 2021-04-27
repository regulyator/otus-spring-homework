package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    private String id = ObjectId.get().toHexString();
    private String caption;

    public Comment(String caption) {
        this.caption = caption;
    }
}
