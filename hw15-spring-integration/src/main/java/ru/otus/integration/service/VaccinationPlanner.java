package ru.otus.integration.service;

import java.util.Date;

public interface VaccinationPlanner {

    Date getVaccinationDate();

    Date getNextVaccineDoseDate(Date date);
}
