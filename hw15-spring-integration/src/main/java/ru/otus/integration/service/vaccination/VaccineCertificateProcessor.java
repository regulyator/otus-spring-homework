package ru.otus.integration.service.vaccination;

import ru.otus.integration.model.VaccineCertificate;
import ru.otus.integration.model.domain.Patient;

public interface VaccineCertificateProcessor {

    VaccineCertificate generateVaccineCertificate(Patient patient);
}
