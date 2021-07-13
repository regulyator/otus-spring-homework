package ru.otus.integration.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.integration.model.domain.Patient;
import ru.otus.integration.model.domain.Polyclinic;
import ru.otus.integration.repository.PatientRepository;
import ru.otus.integration.repository.PolyclinicRepository;

import java.util.Date;

@ChangeLog
public class InitLibraryData {

    @ChangeSet(order = "001", id = "dropDb", author = "regulyator", runAlways = true)
    public void dropDb(MongoDatabase mongoDatabase) {
        mongoDatabase.drop();
    }

    @ChangeSet(order = "002", id = "initPolyclinic", author = "regulyator", runAlways = true)
    public void initPolyclinics(PolyclinicRepository polyclinicRepository) {
        polyclinicRepository.save(Polyclinic.builder()
                .cityKladrCode("100")
                .caption("Moscow")
                .firstVaccineRoom("25")
                .secondVaccineRoom("50")
                .build());

        polyclinicRepository.save(Polyclinic.builder()
                .cityKladrCode("200")
                .caption("Moscow")
                .firstVaccineRoom("1")
                .secondVaccineRoom("2")
                .build());

        polyclinicRepository.save(Polyclinic.builder()
                .cityKladrCode("300")
                .caption("Vladivostok")
                .firstVaccineRoom("101")
                .secondVaccineRoom("202")
                .build());
    }

    @ChangeSet(order = "003", id = "initPatient", author = "regulyator", runAlways = true)
    public void initPatients(PatientRepository patientRepository) {
        patientRepository.save(Patient.builder()
                .fio("Ivanov")
                .cityKladrCode("100")
                .build());

        patientRepository.save(Patient.builder()
                .fio("Petrov")
                .cityKladrCode("100")
                .build());

        patientRepository.save(Patient.builder()
                .fio("Sidorov")
                .cityKladrCode("200")
                .firstVaccineDoseDate(new Date())
                .secondVaccineDoseDate(new Date())
                .build());

        patientRepository.save(Patient.builder()
                .fio("Sharapov")
                .cityKladrCode("300")
                .firstVaccineDoseDate(new Date())
                .secondVaccineDoseDate(new Date())
                .build());

        patientRepository.save(Patient.builder()
                .fio("Abdulov")
                .cityKladrCode("100")
                .firstVaccineDoseDate(new Date())
                .build());

        patientRepository.save(Patient.builder()
                .fio("Dersu Uzala")
                .cityKladrCode("200")
                .firstVaccineDoseDate(new Date())
                .build());

        patientRepository.save(Patient.builder()
                .fio("Sharapova")
                .cityKladrCode("300")
                .firstVaccineDoseDate(new Date())
                .build());

        patientRepository.save(Patient.builder()
                .fio("Bardwel")
                .cityKladrCode("100")
                .firstVaccineDoseDate(new Date())
                .build());

        patientRepository.save(Patient.builder()
                .fio("Obama")
                .cityKladrCode("200")
                .firstVaccineDoseDate(new Date())
                .build());

        patientRepository.save(Patient.builder()
                .fio("Putin")
                .cityKladrCode("100")
                .firstVaccineDoseDate(new Date())
                .secondVaccineDoseDate(new Date())
                .build());

    }
}
