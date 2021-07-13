package ru.otus.integration.model;

import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public class VaccineCertificate {
    private final UUID uuid;
    private final String fio;
    private Date firstVaccineDoseDate;
    private Date secondVaccineDoseDate;
}
