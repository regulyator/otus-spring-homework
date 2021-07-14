package ru.otus.integration.service.vaccination.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.integration.exception.InvalidPatientDataException;
import ru.otus.integration.model.VaccineReminder;
import ru.otus.integration.model.domain.Patient;
import ru.otus.integration.model.domain.Polyclinic;
import ru.otus.integration.service.dataservice.PolyclinicService;
import ru.otus.integration.service.vaccination.VaccinationPlanner;
import ru.otus.integration.service.vaccination.VaccineReminderProcessor;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = VaccineReminderProcessorImpl.class)
@DisplayName(value = " VaccineReminderProcessorImpl should ")
class VaccineReminderProcessorImplTest {
    private final static Date DATE = new Date();
    private final static Patient CORRECT_PATIENT_FIRST_DOSE = Patient.builder()
            .fio("TEST")
            .cityKladrCode("100")
            .build();
    private final static Patient CORRECT_PATIENT_SECOND_DOSE = Patient.builder()
            .fio("TEST")
            .cityKladrCode("100")
            .firstVaccineDoseDate(DATE)
            .build();
    private static final Patient PATIENT_WO_FIO = Patient.builder()
            .cityKladrCode("100")
            .firstVaccineDoseDate(DATE)
            .build();
    private static final Patient PATIENT_WO_KLADR = Patient.builder()
            .fio("TEST")
            .firstVaccineDoseDate(DATE)
            .build();
    private static final Patient PATIENT_NO_NEED_VACCINE = Patient.builder()
            .fio("TEST")
            .cityKladrCode("100")
            .firstVaccineDoseDate(DATE)
            .secondVaccineDoseDate(DATE)
            .build();
    private static final Patient PATIENT_ONLY_SECOND_DOSE = Patient.builder()
            .fio("TEST")
            .cityKladrCode("100")
            .secondVaccineDoseDate(DATE)
            .build();
    private static final Patient FULLY_INVALID_PATIENT = Patient.builder().build();
    private static final Polyclinic POLYCLINIC = Polyclinic.builder()
            .cityKladrCode("100")
            .caption("TEST")
            .firstVaccineRoom("1 room")
            .secondVaccineRoom("2 room").build();
    @Autowired
    private VaccineReminderProcessor vaccineReminderProcessor;
    @MockBean
    private PolyclinicService polyclinicService;
    @MockBean
    private VaccinationPlanner vaccinationPlanner;

    @DisplayName("generate reminder for first dose")
    @Test
    void shouldGenerateReminderForFirstDose() {
        when(polyclinicService.getByKladrCode(POLYCLINIC.getCityKladrCode())).thenReturn(POLYCLINIC);
        when(vaccinationPlanner.getVaccinationDate()).thenReturn(DATE);
        when(vaccinationPlanner.getNextVaccineDoseDate(DATE)).thenReturn(DATE);

        VaccineReminder reminder = vaccineReminderProcessor.generateVaccineReminder(CORRECT_PATIENT_FIRST_DOSE);

        assertThat(reminder).isNotNull()
                .matches(vaccineReminder -> vaccineReminder.getFio().equals(CORRECT_PATIENT_FIRST_DOSE.getFio()))
                .matches(vaccineReminder -> vaccineReminder.getVaccinateDate().equals(DATE))
                .matches(vaccineReminder -> vaccineReminder.getVaccinationStation().contains(POLYCLINIC.getFirstVaccineRoom()));
    }

    @DisplayName("generate reminder for second dose")
    @Test
    void shouldGenerateReminderForSecondDose() {
        when(polyclinicService.getByKladrCode(POLYCLINIC.getCityKladrCode())).thenReturn(POLYCLINIC);
        when(vaccinationPlanner.getNextVaccineDoseDate(DATE)).thenReturn(DATE);
        when(vaccinationPlanner.getNextVaccineDoseDate(DATE)).thenReturn(DATE);

        VaccineReminder reminder = vaccineReminderProcessor.generateVaccineReminder(CORRECT_PATIENT_SECOND_DOSE);

        assertThat(reminder).isNotNull()
                .matches(vaccineReminder -> vaccineReminder.getFio().equals(CORRECT_PATIENT_SECOND_DOSE.getFio()))
                .matches(vaccineReminder -> vaccineReminder.getVaccinateDate().equals(DATE))
                .matches(vaccineReminder -> vaccineReminder.getVaccinationStation().contains(POLYCLINIC.getSecondVaccineRoom()));
    }

    @DisplayName("throws InvalidPatientDataException if patient invalid")
    @Test
    void shouldThrowsInvalidPatientDataExceptionIfPatientInvalid() {
        assertThrows(InvalidPatientDataException.class, () -> vaccineReminderProcessor.generateVaccineReminder(PATIENT_WO_FIO));
        assertThrows(InvalidPatientDataException.class, () -> vaccineReminderProcessor.generateVaccineReminder(PATIENT_WO_KLADR));
        assertThrows(InvalidPatientDataException.class, () -> vaccineReminderProcessor.generateVaccineReminder(PATIENT_NO_NEED_VACCINE));
        assertThrows(InvalidPatientDataException.class, () -> vaccineReminderProcessor.generateVaccineReminder(PATIENT_ONLY_SECOND_DOSE));
        assertThrows(InvalidPatientDataException.class, () -> vaccineReminderProcessor.generateVaccineReminder(FULLY_INVALID_PATIENT));
    }

}