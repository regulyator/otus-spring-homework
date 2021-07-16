package ru.otus.integration.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class VaccineReminder {
    private final String fio;
    private final String vaccinationStation;
    private final Date vaccinateDate;
}
