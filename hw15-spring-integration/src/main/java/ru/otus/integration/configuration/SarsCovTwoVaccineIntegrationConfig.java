package ru.otus.integration.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import ru.otus.integration.model.VaccineCertificate;
import ru.otus.integration.model.VaccineReminder;
import ru.otus.integration.model.domain.Patient;
import ru.otus.integration.service.vaccination.VaccineCertificateProcessor;
import ru.otus.integration.service.vaccination.VaccineReminderProcessor;

import java.util.Objects;

@Configuration
public class SarsCovTwoVaccineIntegrationConfig {

    private static final String VACCINE_REMINDER_MESSAGE = "Patient: %s; Vaccinate station: %s; Planned date: %s";
    private static final String VACCINE_CERTIFICATE_MESSAGE = "Patient: %s; Vaccinate certificate: %s; Vaccinate dates: %s %s";

    private final VaccineCertificateProcessor vaccineCertificateProcessor;
    private final VaccineReminderProcessor vaccineReminderProcessor;

    @Autowired
    public SarsCovTwoVaccineIntegrationConfig(VaccineCertificateProcessor vaccineCertificateProcessor, VaccineReminderProcessor vaccineReminderProcessor) {
        this.vaccineCertificateProcessor = vaccineCertificateProcessor;
        this.vaccineReminderProcessor = vaccineReminderProcessor;
    }

    @Bean
    public QueueChannel patientsInputChannel() {
        return MessageChannels.queue(5).get();
    }

    @Bean
    public QueueChannel certificateChannel() {
        return MessageChannels.queue(5).get();
    }

    @Bean
    public QueueChannel remindersChannel() {
        return MessageChannels.queue(5).get();
    }

    @Bean
    public PublishSubscribeChannel patientsOutputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(1000).get();
    }

    @Bean
    public IntegrationFlow vaccinationFlow() {
        return IntegrationFlows.from(patientsInputChannel())
                .enrichHeaders(h -> h.headerFunction("fullyVaccinated",
                        this::isFullyVaccinated))
                .routeToRecipients(r -> r
                        .recipientFlow("headers.fullyVaccinated",
                                f -> f.handle(vaccineCertificateProcessor, "generateVaccineCertificate")
                                        .channel("certificateChannel"))
                        .recipientFlow("!headers.fullyVaccinated",
                                f -> f.handle(vaccineReminderProcessor, "generateVaccineReminder")
                                        .channel("remindersChannel"))
                        .defaultOutputToParentFlow())
                .get();
    }

    @Bean
    public IntegrationFlow certificateFlow() {
        return IntegrationFlows.from(certificateChannel())
                .transform(VaccineCertificate.class, vc ->
                        String.format(VACCINE_CERTIFICATE_MESSAGE, vc.getFio(), vc.getUuid(), vc.getFirstVaccineDoseDate(), vc.getSecondVaccineDoseDate()))
                .channel("patientsOutputChannel")
                .get();
    }

    @Bean
    public IntegrationFlow remindersFlow() {
        return IntegrationFlows.from(remindersChannel())
                .transform(VaccineReminder.class, vr ->
                        String.format(VACCINE_REMINDER_MESSAGE, vr.getFio(), vr.getVaccinationStation(), vr.getVaccinateDate()))
                .channel(patientsOutputChannel())
                .get();
    }

    @Bean
    public IntegrationFlow errorFlow() {
        return IntegrationFlows.from("customErrorChannel")
                .transform(RuntimeException.class, Throwable::getMessage)
                .channel(patientsOutputChannel())
                .get();
    }

    private Object isFullyVaccinated(Message<Patient> patientMessage) {
        return Objects.nonNull(patientMessage.getPayload().getFirstVaccineDoseDate())
                && Objects.nonNull(patientMessage.getPayload().getSecondVaccineDoseDate());
    }
}
