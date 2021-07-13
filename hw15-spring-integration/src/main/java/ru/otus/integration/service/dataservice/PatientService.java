package ru.otus.integration.service.dataservice;

import ru.otus.integration.model.domain.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> getAll();
}
