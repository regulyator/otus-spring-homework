package ru.otus.icdimporter.model.domain.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("Icd")
public class IcdDocument {
    @Id
    private String id;
    @Field("recCode")
    private String recCode;
    @Field("mbkName")
    private String mbkName;
    @Field("actual")
    private Boolean actual;
    @Field("mkbCode")
    private String mkbCode;
    @Field("parentIcdRecCode")
    private String parentIcdRecCode;

}

