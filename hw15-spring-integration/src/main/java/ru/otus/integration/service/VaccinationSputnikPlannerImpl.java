package ru.otus.integration.service;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class VaccinationSputnikPlannerImpl implements VaccinationPlanner {
    private static final int SPUTNIK_SECOND_VACCINE_PERIOD_DAYS = 21;
    private static final int FIRST_VACCINE_LAG_DAYS = 1;
    private final Calendar startCalendar = Calendar.getInstance();

    @Override
    public Date getVaccinationDate() {
        this.startCalendar.add(Calendar.DATE, FIRST_VACCINE_LAG_DAYS);
        return this.startCalendar.getTime();

    }

    @Override
    public Date getNextVaccineDoseDate(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, SPUTNIK_SECOND_VACCINE_PERIOD_DAYS);
        return calendar.getTime();
    }
}
