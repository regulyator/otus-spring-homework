package ru.otus.questions.services.util;

public interface InputOutputService {

    void writeOutput(String outputMessage);

    String readInput(String promptMessage);

    String readInput();
}
