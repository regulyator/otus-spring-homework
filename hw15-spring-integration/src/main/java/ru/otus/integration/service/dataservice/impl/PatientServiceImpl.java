package ru.otus.integration.service.dataservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.integration.exception.EntityNotFoundException;
import ru.otus.integration.model.domain.Patient;
import ru.otus.integration.repository.PatientRepository;
import ru.otus.integration.service.dataservice.PatientService;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    @Override
    public Patient getPatientById(String patientId) {
        return patientRepository.findById(patientId).orElseThrow(EntityNotFoundException::new);
    }
}
