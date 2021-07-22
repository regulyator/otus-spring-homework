package ru.otus.library.service.healthcheck;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class EvenMinutesCheck implements HealthIndicator {

    @Override
    public Health health() {
        final int nowMinutes = Calendar.getInstance().get(Calendar.MINUTE);
        if ((nowMinutes % 2) == 0) {
            return Health.up().withDetail("All ok, minutes is even", nowMinutes).build();
        } else {
            return Health.down().withDetail("Sorry, minutes is odd, try in next minute", nowMinutes).build();
        }
    }
}
