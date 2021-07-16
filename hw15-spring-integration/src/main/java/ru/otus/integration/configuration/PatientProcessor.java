package ru.otus.integration.configuration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.integration.model.domain.Patient;

@MessagingGateway(errorChannel = "customErrorChannel")
public interface PatientProcessor {

    @Gateway(requestChannel = "patientsInputChannel", replyChannel = "patientsOutputChannel")
    String process(Patient patient);
}
