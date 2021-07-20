package ru.otus.integration.service.integration;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;
import ru.otus.integration.model.VaccineCertificate;

@Component
public class VaccineCertificateTransformer implements GenericTransformer<VaccineCertificate, String> {
    private static final String VACCINE_CERTIFICATE_MESSAGE = "Patient: %s; Vaccinate certificate: %s; Vaccinate dates: %s %s";
    @Override
    public String transform(VaccineCertificate vaccineCertificate) {
        return String.format(VACCINE_CERTIFICATE_MESSAGE, vaccineCertificate.getFio(), vaccineCertificate.getUuid(), vaccineCertificate.getFirstVaccineDoseDate(), vaccineCertificate.getSecondVaccineDoseDate());
    }
}
