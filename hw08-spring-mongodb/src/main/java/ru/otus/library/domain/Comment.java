package ru.otus.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "Comments")
public class Comment {
    @EqualsAndHashCode.Include
    @Id
    private String id;
    @EqualsAndHashCode.Include
    @Field("caption")
    private String caption;
    @Field("book")
    private Book book;
}
