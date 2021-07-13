package ru.otus.integration.service.dataservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.integration.exception.EntityNotFoundException;
import ru.otus.integration.model.domain.Polyclinic;
import ru.otus.integration.repository.PolyclinicRepository;
import ru.otus.integration.service.dataservice.PolyclinicService;

import java.util.List;

@Service
public class PolyclinicServiceImpl implements PolyclinicService {
    private final PolyclinicRepository polyclinicRepository;

    @Autowired
    public PolyclinicServiceImpl(PolyclinicRepository polyclinicRepository) {
        this.polyclinicRepository = polyclinicRepository;
    }

    @Override
    public List<Polyclinic> getAll() {
        return polyclinicRepository.findAll();
    }

    @Override
    public Polyclinic getByKladrCode(String cityKladrCode) {
        return polyclinicRepository.findByCityKladrCode(cityKladrCode).orElseThrow(EntityNotFoundException::new);

    }
}
