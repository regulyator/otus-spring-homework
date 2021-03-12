package ru.otus.questions.services.util;

public interface InputOutputService {

    void writeOutput(String outputMessage);

    void writeOutput(String outputMessage, Object... args);

    String readInput(String promptMessage);

    String readInput(String promptMessage, Object... args);

    String readInput();
}
