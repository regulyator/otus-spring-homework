package ru.otus.integration.service.vaccination;

import ru.otus.integration.model.VaccineReminder;
import ru.otus.integration.model.domain.Patient;

public interface VaccineReminderProcessor {

    VaccineReminder generateVaccineReminder(Patient patient);
}
