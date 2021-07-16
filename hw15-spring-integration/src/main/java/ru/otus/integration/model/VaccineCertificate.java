package ru.otus.integration.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
public class VaccineCertificate {
    private final UUID uuid;
    private final String fio;
    private final Date firstVaccineDoseDate;
    private final Date secondVaccineDoseDate;
}
