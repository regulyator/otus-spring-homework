package ru.otus.integration.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.integration.model.domain.Polyclinic;

import java.util.Optional;

public interface PolyclinicRepository extends MongoRepository<Polyclinic, String> {

    Optional<Polyclinic> findByCityKladrCode(String cityKladrCode);
}
