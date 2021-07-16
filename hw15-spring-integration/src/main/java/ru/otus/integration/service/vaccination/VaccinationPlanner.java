package ru.otus.integration.service.vaccination;

import java.util.Date;

public interface VaccinationPlanner {

    Date getVaccinationDate();

    Date getNextVaccineDoseDate(Date date);
}
