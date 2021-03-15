package ru.otus.questions.services.util.impl;

import org.springframework.stereotype.Service;
import ru.otus.questions.services.util.InputOutputFacade;
import ru.otus.questions.services.util.InputOutputService;
import ru.otus.questions.services.util.LocalizationService;

@Service
public class InputOutputFacadeImpl implements InputOutputFacade {

    private final LocalizationService localizationService;
    private final InputOutputService inputOutputService;

    public InputOutputFacadeImpl(LocalizationService localizationService, InputOutputService inputOutputService) {
        this.localizationService = localizationService;
        this.inputOutputService = inputOutputService;
    }


    @Override
    public void printMessageToUser(String outputMessage) {
        inputOutputService.writeOutput(localizationService.getLocalizedMessage(outputMessage));
    }

    @Override
    public void printMessageToUser(String outputMessage, Object... args) {
        inputOutputService.writeOutput(localizationService.getLocalizedMessage(outputMessage, args));
    }

    @Override
    public String readInputFromUser(String promptMessage) {
        return inputOutputService.readInput(localizationService.getLocalizedMessage(promptMessage));
    }

    @Override
    public String readInputFromUser(String promptMessage, Object... args) {
        return inputOutputService.readInput(localizationService.getLocalizedMessage(promptMessage, args));
    }

    @Override
    public String readInputFromUser() {
        return inputOutputService.readInput();
    }
}
