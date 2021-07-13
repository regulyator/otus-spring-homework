package ru.otus.integration.configuration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.model.domain.Patient;

@MessagingGateway
public interface PatientProcessor {

    @Gateway(requestChannel = "patientsInputChannel", replyChannel = "patientsOutputChannel")
    Object process(Patient patient);
}
