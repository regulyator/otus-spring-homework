package ru.otus.questions.services.util;

public interface InputOutputLocalizedService {

    void printMessageToUser(String outputMessage);

    void printMessageToUser(String outputMessage, Object... args);

    String readInputFromUser(String promptMessage);

    String readInputFromUser(String promptMessage, Object... args);

    String readInputFromUser();
}
