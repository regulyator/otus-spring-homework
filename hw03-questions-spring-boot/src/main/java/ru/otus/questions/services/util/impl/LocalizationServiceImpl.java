package ru.otus.questions.services.util.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.questions.services.util.LocaleHolder;
import ru.otus.questions.services.util.LocalizationService;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;
    private final LocaleHolder localeHolder;

    @Autowired
    public LocalizationServiceImpl(MessageSource messageSource,
                                   LocaleHolder localeHolder) {
        this.messageSource = messageSource;
        this.localeHolder = localeHolder;
    }

    @Override
    public String getLocalizedMessage(String messageCode) {
        return getLocalizedMessage(messageCode, (Object[]) null);
    }

    @Override
    public String getLocalizedMessage(String messageCode, Object... args) {
        return messageSource.getMessage(messageCode, args, messageCode, localeHolder.getAppLocale());
    }
}
