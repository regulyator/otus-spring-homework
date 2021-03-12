package ru.otus.questions.services.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.questions.services.util.LocalizationService;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;
    private final Locale locale;

    @Autowired
    public LocalizationServiceImpl(MessageSource messageSource,
                                   @Value("${ru.otus.hw.locale}") Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public String getLocalizedMessage(String messageCode) {
        return getLocalizedMessage(messageCode, (Object[]) null);
    }

    @Override
    public String getLocalizedMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, messageCode, locale);
    }
}
