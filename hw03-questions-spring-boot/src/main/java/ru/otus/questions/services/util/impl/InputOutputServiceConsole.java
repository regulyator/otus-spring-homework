package ru.otus.questions.services.util.impl;

import ru.otus.questions.services.util.InputOutputService;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputOutputServiceConsole implements InputOutputService {

    private final InputStream in;
    private final PrintStream out;

    public InputOutputServiceConsole(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
    }


    @Override
    public void writeOutput(String outputMessage) {
        out.println(outputMessage);
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

    @Override
    public String readInput(String promptMessage) {
        writeOutput(promptMessage);
        return readInput();
    }
}
