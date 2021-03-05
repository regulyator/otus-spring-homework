package ru.otus.questions.services.util.impl;

import org.springframework.stereotype.Service;
import ru.otus.questions.services.util.InputOutputService;

import java.util.Scanner;

@Service
public class InputOutputServiceConsole implements InputOutputService {

    @Override
    public void writeOutput(String outputMessage) {
        System.out.println(outputMessage);
    }

    @Override
    public String readInput() {
        Scanner scanner = new Scanner(System.in);
        String result = scanner.nextLine();
        while (result.isEmpty()) {

            result = scanner.nextLine();
        }
        return result;
    }
}
