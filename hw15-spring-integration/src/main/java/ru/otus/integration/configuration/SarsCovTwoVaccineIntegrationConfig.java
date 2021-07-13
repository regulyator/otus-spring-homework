package ru.otus.integration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@Configuration
public class SarsCovTwoVaccineIntegrationConfig {

    @Bean
    public QueueChannel patientsInputChannel() {
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
        return IntegrationFlows.from("patientsInputChannel")
                .enrichHeaders(h -> h.headerExpression("fullyVaccinated",
                        "payload.firstVaccineDoseDate != null && payload.secondVaccineDoseDate != null"))
                .routeToRecipients(r -> r
                        .recipientFlow("headers.fullyVaccinated",
                                f -> f.handle("vaccineCertificateProcessorImpl", "generateVaccineCertificate")
                                        .channel("patientsOutputChannel"))
                        .recipientFlow("!headers.fullyVaccinated",
                                f -> f.handle("vaccineReminderProcessorImpl", "generateVaccineReminder")
                                        .channel("patientsOutputChannel"))
                        .defaultOutputToParentFlow())
                .get();
    }
}
