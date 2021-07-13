package ru.otus.integration.model;

import lombok.Builder;

import java.util.Date;

@Builder
public class VaccineReminder {
    private final String fio;
    private final String vaccinationStation;
    private final Date vaccinateDate;
}
