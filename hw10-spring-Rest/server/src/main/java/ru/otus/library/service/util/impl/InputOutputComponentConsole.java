package ru.otus.library.service.util.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.library.service.util.InputOutputComponent;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Component
public class InputOutputComponentConsole implements InputOutputComponent {

    private final InputStream in;
    private final PrintStream out;

    public InputOutputComponentConsole(@Value("#{ T(java.lang.System).in}") InputStream in,
                                       @Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
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
