package ru.otus.integration.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("Patient")
public class Patient {
    @Id
    private String id;
    @Field("fio")
    private String fio;
    @Field("cityKladrCode")
    private String cityKladrCode;
    @Field("firstVaccineDoseDate")
    private Date firstVaccineDoseDate;
    @Field("secondVaccineDoseDate")
    private Date secondVaccineDoseDate;
}
