package ru.otus.questions.services.util;

public interface InputOutputService {

    void writeOutput(String outputMessage);

    void writeOutput(String outputMessage, String... args);

    String readInput(String promptMessage);

    String readInput(String promptMessage, String... args);

    String readInput();
}
