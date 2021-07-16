package ru.otus.integration.service.vaccination.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.integration.service.vaccination.VaccinationPlanner;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = VaccinationSputnikPlannerImpl.class)
@DisplayName(value = "VaccinationSputnikPlannerImpl should ")
class VaccinationSputnikPlannerImplTest {
    private static final int ONE_DAY_IN_MILLISECONDS = 86400000;
    private static final int TWENTY_ONE_DAYS_IN_MILLISECONDS = ONE_DAY_IN_MILLISECONDS * 21;
    @Autowired
    private VaccinationPlanner vaccinationPlanner;

    @DisplayName("return date")
    @Test
    void shouldReturnDate() {
        Date date = vaccinationPlanner.getVaccinationDate();

        assertThat(date).isNotNull();
    }

    @DisplayName("increment by one day returning dates")
    @Test
    void shouldIncrementsByOneDayReturningDates() {
        Date date1 = vaccinationPlanner.getVaccinationDate();
        Date date2 = vaccinationPlanner.getVaccinationDate();
        Date date3 = vaccinationPlanner.getVaccinationDate();

        assertThat(date2.getTime() - date1.getTime()).isEqualTo(ONE_DAY_IN_MILLISECONDS);
        assertThat(date3.getTime() - date2.getTime()).isEqualTo(ONE_DAY_IN_MILLISECONDS);
    }

    @DisplayName("increment by 21 date returning dates on receive date")
    @Test
    void shouldIncrementsByTwentyOneDayReturningDatesOnReceiveDate() {
        Date startDate = new Date();
        Date date = vaccinationPlanner.getNextVaccineDoseDate(startDate);

        assertThat(date.getTime() - startDate.getTime()).isEqualTo(TWENTY_ONE_DAYS_IN_MILLISECONDS);
    }


}