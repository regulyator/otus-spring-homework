package ru.otus.questions.services.util.impl;

import org.springframework.stereotype.Service;
import ru.otus.questions.services.util.InputOutputComponent;
import ru.otus.questions.services.util.InputOutputLocalizedService;
import ru.otus.questions.services.util.LocalizationService;

@Service
public class InputOutputLocalizedServiceImpl implements InputOutputLocalizedService {

    private final LocalizationService localizationService;
    private final InputOutputComponent inputOutputComponent;

    public InputOutputLocalizedServiceImpl(LocalizationService localizationService, InputOutputComponent inputOutputComponent) {
        this.localizationService = localizationService;
        this.inputOutputComponent = inputOutputComponent;
    }


    @Override
    public void printMessageToUser(String outputMessage) {
        inputOutputComponent.writeOutput(localizationService.getLocalizedMessage(outputMessage));
    }

    @Override
    public void printMessageToUser(String outputMessage, Object... args) {
        inputOutputComponent.writeOutput(localizationService.getLocalizedMessage(outputMessage, args));
    }

    @Override
    public String readInputFromUser(String promptMessage) {
        return inputOutputComponent.readInput(localizationService.getLocalizedMessage(promptMessage));
    }

    @Override
    public String readInputFromUser(String promptMessage, Object... args) {
        return inputOutputComponent.readInput(localizationService.getLocalizedMessage(promptMessage, args));
    }

    @Override
    public String readInputFromUser() {
        return inputOutputComponent.readInput();
    }
}
