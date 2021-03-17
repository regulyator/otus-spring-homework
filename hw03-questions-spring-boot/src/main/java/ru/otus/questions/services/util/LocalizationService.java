package ru.otus.questions.services.util;

public interface LocalizationService {

    String getLocalizedMessage(String messageCode);

    String getLocalizedMessage(String messageCode, Object... args);
}
