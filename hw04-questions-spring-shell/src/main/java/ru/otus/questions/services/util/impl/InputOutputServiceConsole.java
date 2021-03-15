package ru.otus.questions.services.util.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.questions.anotation.LoggingMethod;
import ru.otus.questions.services.util.InputOutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
@Component
public class InputOutputServiceConsole implements InputOutputService {

    private final InputStream in;
    private final PrintStream out;

    public InputOutputServiceConsole(@Value("#{ T(java.lang.System).in}") InputStream in,
                                     @Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    @LoggingMethod
    public void writeOutput(String outputMessage) {
        out.println(outputMessage);
    }


    @Override
    public String readInput(String promptMessage) {
        writeOutput(promptMessage);
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
