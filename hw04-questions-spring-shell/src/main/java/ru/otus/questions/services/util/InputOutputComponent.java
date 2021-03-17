package ru.otus.questions.services.util;

public interface InputOutputComponent {

    void writeOutput(String outputMessage);

    String readInput(String promptMessage);

    String readInput();
}
