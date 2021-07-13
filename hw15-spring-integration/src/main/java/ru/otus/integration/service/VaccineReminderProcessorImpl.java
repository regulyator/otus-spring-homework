package ru.otus.integration.service;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import ru.otus.integration.exception.InvalidPatientDataException;
import ru.otus.integration.model.VaccineReminder;
import ru.otus.integration.model.domain.Patient;
import ru.otus.integration.model.domain.Polyclinic;
import ru.otus.integration.service.dataservice.PolyclinicService;

import java.util.Date;
import java.util.Objects;

@Component
public class VaccineReminderProcessorImpl implements VaccineReminderProcessor {
    private final PolyclinicService polyclinicService;
    private final VaccinationPlanner vaccinationPlanner;

    public VaccineReminderProcessorImpl(PolyclinicService polyclinicService,
                                        VaccinationPlanner vaccinationPlanner) {
        this.polyclinicService = polyclinicService;
        this.vaccinationPlanner = vaccinationPlanner;
    }

    @Override
    public VaccineReminder generateVaccineReminder(@NonNull Patient patient) {
        if(checkPatientDataForInvalidValues(patient)){
            throw new InvalidPatientDataException("Invalid Patient data! Check patient FIO, City KLADR Code and vaccine dates.");
        } else {

            new Date().s;
            final Polyclinic polyclinic = polyclinicService.getByKladrCode(patient.getCityKladrCode());
            final Date reminderDate = Objects.isNull(patient.getFirstVaccineDoseDate()) ? vaccinationPlanner.getVaccinationDate():vaccinationPlanner.getNextVaccineDoseDate()
            final String vaccinateStation = String.format("Address: %s room: %s", polyclinic.getCaption(),)
            VaccineReminder.builder()
                    .fio(patient.getFio())
                    .
        }
        return null;
    }

    private boolean checkPatientDataForInvalidValues(Patient patient) {
        return Objects.isNull(patient.getFio())
                || Objects.isNull(patient.getCityKladrCode())
                || (Objects.nonNull(patient.getFirstVaccineDoseDate()) && Objects.nonNull(patient.getSecondVaccineDoseDate()))
                || (Objects.isNull(patient.getFirstVaccineDoseDate()) && Objects.nonNull(patient.getSecondVaccineDoseDate()));
    }
}
