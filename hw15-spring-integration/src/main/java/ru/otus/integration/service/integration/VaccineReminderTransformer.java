package ru.otus.integration.service.integration;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;
import ru.otus.integration.model.VaccineCertificate;
import ru.otus.integration.model.VaccineReminder;

@Component
public class VaccineReminderTransformer implements GenericTransformer<VaccineReminder, String> {
    private static final String VACCINE_REMINDER_MESSAGE = "Patient: %s; Vaccinate station: %s; Planned date: %s";
    @Override
    public String transform(VaccineReminder vaccineReminder) {
        return String.format(VACCINE_REMINDER_MESSAGE, vaccineReminder.getFio(), vaccineReminder.getVaccinationStation(), vaccineReminder.getVaccinateDate());
    }
}
