package ru.otus.questions.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.questions.services.util.InputOutputService;
import ru.otus.questions.services.util.impl.InputOutputServiceConsole;

@Configuration
public class QuizConfiguration {

    @Bean
    public InputOutputService inputOutputService(MessageSource messageSource) {
        return new InputOutputServiceConsole(System.in, System.out, messageSource);
    }
}
