package ru.otus.integration.shell;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.integration.configuration.PatientProcessor;
import ru.otus.integration.service.dataservice.PatientService;

@ShellComponent
@ShellCommandGroup("Patient vaccination command")
public class PatientVaccinationCommands {

    private final PatientService patientService;
    private final PatientProcessor patientProcessor;

    public PatientVaccinationCommands(PatientService patientService,
                                      PatientProcessor patientProcessor) {
        this.patientService = patientService;
        this.patientProcessor = patientProcessor;
    }

    @ShellMethod(key = "patients", value = "Show all patients")
    public void getAllPatients() {
        patientService.getAll().forEach(author -> System.out.println(author.toString()));
    }

    @ShellMethod(key = "process-patient", value = "Process patient")
    public void processPatient(@ShellOption(value = {"patientId"}) String patientId){
        Object processResult = patientProcessor.process(patientService.getPatientById(patientId));
        System.out.println(processResult);
    }


}
