package ru.otus.integration.service.vaccination.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.integration.exception.InvalidPatientDataForCertificateException;
import ru.otus.integration.model.VaccineCertificate;
import ru.otus.integration.model.domain.Patient;
import ru.otus.integration.service.vaccination.VaccineCertificateProcessor;

import java.util.Date;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = VaccineCertificateProcessorImpl.class)
@DisplayName(value = "VaccineCertificateProcessorImpl should ")
class VaccineCertificateProcessorImplTest {
    private final static Date DATE = new Date();
    private final static Patient CORRECT_PATIENT = Patient.builder()
            .fio("TEST")
            .firstVaccineDoseDate(DATE)
            .secondVaccineDoseDate(DATE)
            .build();
    private static final Patient PATIENT_WO_FIO = Patient.builder()
            .firstVaccineDoseDate(DATE)
            .secondVaccineDoseDate(DATE)
            .build();
    private static final Patient PATIENT_WO_FIRST_DATE = Patient.builder()
            .fio("TEST")
            .secondVaccineDoseDate(DATE)
            .build();
    private static final Patient PATIENT_WO_SECOND_DATE = Patient.builder()
            .fio("TEST")
            .firstVaccineDoseDate(DATE)
            .build();
    private static final Patient FULLY_INVALID_PATIENT = Patient.builder().build();
    @Autowired
    private VaccineCertificateProcessor vaccineCertificateProcessor;

    @DisplayName("return vaccine certificate")
    @Test
    void shouldReturnVaccineCertificate() {
        VaccineCertificate certificate = vaccineCertificateProcessor.generateVaccineCertificate(CORRECT_PATIENT);

        assertThat(certificate).isNotNull()
                .matches(vaccineCertificate -> vaccineCertificate.getFirstVaccineDoseDate().equals(DATE))
                .matches(vaccineCertificate -> vaccineCertificate.getSecondVaccineDoseDate().equals(DATE))
                .matches(vaccineCertificate -> vaccineCertificate.getFio().equals(CORRECT_PATIENT.getFio()))
                .matches(vaccineCertificate -> Objects.nonNull(vaccineCertificate.getUuid()));
    }

    @DisplayName("throws InvalidPatientDataForCertificateException if patient invalid")
    @Test
    void shouldThrowsInvalidPatientDataForCertificateExceptionIfPatientInvalid() {
        assertThrows(InvalidPatientDataForCertificateException.class, () -> vaccineCertificateProcessor.generateVaccineCertificate(PATIENT_WO_FIO));
        assertThrows(InvalidPatientDataForCertificateException.class, () -> vaccineCertificateProcessor.generateVaccineCertificate(PATIENT_WO_FIRST_DATE));
        assertThrows(InvalidPatientDataForCertificateException.class, () -> vaccineCertificateProcessor.generateVaccineCertificate(PATIENT_WO_SECOND_DATE));
        assertThrows(InvalidPatientDataForCertificateException.class, () -> vaccineCertificateProcessor.generateVaccineCertificate(FULLY_INVALID_PATIENT));
    }


}