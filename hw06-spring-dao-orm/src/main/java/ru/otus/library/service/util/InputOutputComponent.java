package ru.otus.library.service.util;

public interface InputOutputComponent {

    void writeOutput(String outputMessage);

    String readInput(String promptMessage);

    String readInput();
}
