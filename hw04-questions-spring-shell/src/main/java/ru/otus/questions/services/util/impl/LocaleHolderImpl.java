package ru.otus.questions.services.util.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.questions.services.util.LocaleHolder;

import java.util.Locale;

@Component
public class LocaleHolderImpl implements LocaleHolder {
    private final Locale locale;

    public LocaleHolderImpl(@Value("${ru.otus.hw.locale}") Locale locale) {
        this.locale = locale;
    }

    @Override
    public Locale getAppLocale() {
        return locale;
    }
}
