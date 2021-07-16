package ru.otus.integration.service.vaccination.impl;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.integration.exception.InvalidPatientDataException;
import ru.otus.integration.model.VaccineReminder;
import ru.otus.integration.model.domain.Patient;
import ru.otus.integration.model.domain.Polyclinic;
import ru.otus.integration.service.dataservice.PolyclinicService;
import ru.otus.integration.service.vaccination.VaccinationPlanner;
import ru.otus.integration.service.vaccination.VaccineReminderProcessor;

import java.util.Date;
import java.util.Objects;

@Service
public class VaccineReminderProcessorImpl implements VaccineReminderProcessor {
    private static final String VACCINATE_STATION_FULL_ADDRESS = "Address: %s room: %s";
    private final PolyclinicService polyclinicService;
    private final VaccinationPlanner vaccinationPlanner;

    public VaccineReminderProcessorImpl(PolyclinicService polyclinicService,
                                        VaccinationPlanner vaccinationPlanner) {
        this.polyclinicService = polyclinicService;
        this.vaccinationPlanner = vaccinationPlanner;
    }

    @Override
    public VaccineReminder generateVaccineReminder(@NonNull Patient patient) {
        if (checkPatientDataForInvalidValues(patient)) {
            throw new InvalidPatientDataException("Invalid Patient data! Check patient FIO, City KLADR Code and vaccine dates.");
        } else {
            final Polyclinic polyclinic = polyclinicService.getByKladrCode(patient.getCityKladrCode());
            final boolean isFirstVaccineDose = checkIsFirstVaccineDose(patient);
            return VaccineReminder.builder()
                    .fio(patient.getFio())
                    .vaccinateDate(getVaccinationDate(patient, isFirstVaccineDose))
                    .vaccinationStation(getVaccinateStation(polyclinic, isFirstVaccineDose))
                    .build();
        }
    }

    private Date getVaccinationDate(Patient patient, boolean isFirstVaccineDose) {
        return isFirstVaccineDose ?
                vaccinationPlanner.getVaccinationDate() :
                vaccinationPlanner.getNextVaccineDoseDate(patient.getFirstVaccineDoseDate());
    }

    private String getVaccinateStation(Polyclinic polyclinic, boolean isFirstVaccineDose) {
        return String.format(VACCINATE_STATION_FULL_ADDRESS,
                polyclinic.getCaption(),
                isFirstVaccineDose ? polyclinic.getFirstVaccineRoom() : polyclinic.getSecondVaccineRoom());
    }

    private boolean checkIsFirstVaccineDose(Patient patient) {
        return Objects.isNull(patient.getFirstVaccineDoseDate());
    }

    private boolean checkPatientDataForInvalidValues(Patient patient) {
        return Objects.isNull(patient.getFio())
                || Objects.isNull(patient.getCityKladrCode())
                || (Objects.nonNull(patient.getFirstVaccineDoseDate()) && Objects.nonNull(patient.getSecondVaccineDoseDate()))
                || (Objects.isNull(patient.getFirstVaccineDoseDate()) && Objects.nonNull(patient.getSecondVaccineDoseDate()));
    }
}
