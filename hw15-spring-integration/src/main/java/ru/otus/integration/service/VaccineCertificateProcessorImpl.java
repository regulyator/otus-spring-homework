package ru.otus.integration.service;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.otus.integration.exception.InvalidPatientDataForCertificateException;
import ru.otus.integration.model.VaccineCertificate;
import ru.otus.integration.model.domain.Patient;

import java.util.Objects;
import java.util.UUID;

@Component
public class VaccineCertificateProcessorImpl implements VaccineCertificateProcessor {

    @Override
    public VaccineCertificate generateVaccineCertificate(@NonNull Patient patient) {
        if (Objects.isNull(patient.getFio())
                || Objects.isNull(patient.getFirstVaccineDoseDate())
                || Objects.isNull(patient.getSecondVaccineDoseDate())) {
            throw new InvalidPatientDataForCertificateException("Invalid patient data! Check Patient FIO and date of vaccine.");
        } else {
            return VaccineCertificate.builder()
                    .uuid(UUID.randomUUID())
                    .fio(patient.getFio())
                    .firstVaccineDoseDate(patient.getFirstVaccineDoseDate())
                    .secondVaccineDoseDate(patient.getSecondVaccineDoseDate())
                    .build();
        }
    }
}
