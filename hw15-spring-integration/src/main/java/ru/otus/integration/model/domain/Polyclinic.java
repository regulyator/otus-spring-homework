package ru.otus.integration.model.domain;

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
@Document("Polyclinic")
public class Polyclinic {
    @Id
    private String id;
    @Field("caption")
    private String caption;
    @Field("cityKladrCode")
    private String cityKladrCode;
    @Field("firstVaccineRoom")
    private String firstVaccineRoom;
    @Field("secondVaccineRoom")
    private String secondVaccineRoom;
}
