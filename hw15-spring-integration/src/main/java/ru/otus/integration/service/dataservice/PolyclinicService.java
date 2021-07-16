package ru.otus.integration.service.dataservice;

import ru.otus.integration.model.domain.Polyclinic;

import java.util.List;

public interface PolyclinicService {

    List<Polyclinic> getAll();

    Polyclinic getByKladrCode(String cityKladrCode);
}
