package ru.otus.integration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.integration.model.domain.Patient;

public interface PatientRepository extends MongoRepository<Patient, String> {

}
