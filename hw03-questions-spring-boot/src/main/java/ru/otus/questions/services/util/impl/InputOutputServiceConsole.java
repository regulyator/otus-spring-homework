package ru.otus.questions.services.util.impl;

import org.springframework.context.MessageSource;
import ru.otus.questions.anotation.LoggingMethod;
import ru.otus.questions.services.util.InputOutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class InputOutputServiceConsole implements InputOutputService {

    private final InputStream in;
    private final PrintStream out;
    private final MessageSource messageSource;

    public InputOutputServiceConsole(InputStream in, PrintStream out, MessageSource messageSource) {
        this.in = in;
        this.out = out;
        this.messageSource = messageSource;
    }


    @Override
    @LoggingMethod
    public void writeOutput(String outputMessage) {
        writeOutput(outputMessage, null);
    }

    @Override
    public void writeOutput(String outputMessage, String... args) {
        out.println(messageSource.getMessage(outputMessage, args, Locale.getDefault()));
    }

    @Override
    public String readInput(String promptMessage) {
        return readInput(promptMessage, null);
    }

    @Override
    public String readInput(String promptMessage, String... args) {
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
