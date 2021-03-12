package ru.otus.questions.services.util.impl;

import ru.otus.questions.anotation.LoggingMethod;
import ru.otus.questions.services.util.InputOutputService;
import ru.otus.questions.services.util.LocalizationService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputOutputServiceConsole implements InputOutputService {

    private final InputStream in;
    private final PrintStream out;
    private final LocalizationService localizationService;

    public InputOutputServiceConsole(InputStream in, PrintStream out, LocalizationService localizationService) {
        this.in = in;
        this.out = out;
        this.localizationService = localizationService;
    }


    @Override
    @LoggingMethod
    public void writeOutput(String outputMessage) {
        writeOutput(outputMessage, (Object[]) null);
    }

    @Override
    public void writeOutput(String outputMessage, Object... args) {
        out.println(localizationService.getLocalizedMessage(outputMessage, args));
    }

    @Override
    public String readInput(String promptMessage) {
        return readInput(promptMessage, (Object[]) null);
    }

    @Override
    public String readInput(String promptMessage, Object... args) {
        writeOutput(promptMessage, args);
        return readInput();
    }

    @Override
    public String readInput() {
        Scanner scanner = new Scanner(in);
        String result = scanner.nextLine();
        while (result.isEmpty()) {

            result = scanner.nextLine();
        }
        return result;
    }
}
