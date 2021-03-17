package ru.otus.questions.services.execution.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.util.InputOutputLocalizedService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnswerReaderImpl implements AnswerReader<Answer> {
    private final static String USER_ANSWER_DELIMITER = ",";
    private final static String QUIT_SYMBOL = "Q";
    private final InputOutputLocalizedService inputOutputLocalizedService;

    @Autowired
    public AnswerReaderImpl(InputOutputLocalizedService inputOutputLocalizedService) {
        this.inputOutputLocalizedService = inputOutputLocalizedService;
    }

    @Override
    public List<Answer> readUserAnswers(Map<Integer, Answer> answersMap) {
        writeGreetings();

        var userInput = inputOutputLocalizedService.readInputFromUser();
        if (Objects.nonNull(userInput) && userInput.equals(QUIT_SYMBOL)) {
            return Collections.emptyList();
        }

        var convertedAnswers = convertRawUserInput(userInput, answersMap);
        while (convertedAnswers.isEmpty()) {
            inputOutputLocalizedService.printMessageToUser("WrongAnswer");
            writeGreetings();
            userInput = inputOutputLocalizedService.readInputFromUser();
            if (Objects.nonNull(userInput) && userInput.equals(QUIT_SYMBOL)) {
                return Collections.emptyList();
            }
            convertedAnswers = convertRawUserInput(userInput, answersMap);
        }

        return convertedAnswers;
    }

    /**
     * тут пробуем сконвертировать ответы пользователя
     *
     * @param result     строка ввода
     * @param answersMap мапа с ответами
     * @return выбранные ответы или пустой лист если что то не так
     */
    private List<Answer> convertRawUserInput(String result, Map<Integer, Answer> answersMap) {
        try {
            var intResults = Arrays.stream(result
                    .replace(" ", "")
                    .trim()
                    .split(USER_ANSWER_DELIMITER)).map(Integer::valueOf).collect(Collectors.toList());

            if (intResults.stream().allMatch(answersMap::containsKey)) {
                return intResults.stream()
                        .map(answersMap::get)
                        .collect(Collectors.toList());
            } else {
                return Collections.emptyList();
            }
        } catch (NumberFormatException ex) {
            return Collections.emptyList();
        }
    }

    private void writeGreetings() {
        inputOutputLocalizedService.printMessageToUser("AnswerAdvice");
    }
}
