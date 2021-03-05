package ru.otus.questions.services.execution.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.questions.domain.Answer;
import ru.otus.questions.services.execution.AnswerReader;
import ru.otus.questions.services.util.InputOutputService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnswerReaderImpl implements AnswerReader<Integer> {
    private final static String USER_ANSWER_DELIMITER = ",";
    private final InputOutputService inputOutputServiceConsole;

    @Autowired
    public AnswerReaderImpl(InputOutputService inputOutputServiceConsole) {
        this.inputOutputServiceConsole = inputOutputServiceConsole;
    }

    @Override
    public List<Integer> readUserAnswers(Map<Integer, Answer> answersMap) {
        inputOutputServiceConsole.writeOutput("Enter answer number(s) , for multiply answer use comma fo separate: ");
        List<Integer> convertedAnswersNumbers = checkResult(inputOutputServiceConsole.readInput(), answersMap);
        while (convertedAnswersNumbers.isEmpty()) {
            inputOutputServiceConsole.writeOutput("Wrong answer format or no such answer number!");
            inputOutputServiceConsole.writeOutput("Enter answer number(s) , for multiply answer use comma fo separate: ");
            convertedAnswersNumbers = checkResult(inputOutputServiceConsole.readInput(), answersMap);
        }
        return convertedAnswersNumbers;
    }

    private List<Integer> checkResult(String result, Map<Integer, Answer> questionMap) {
        try {
            List<Integer> intResults = Arrays.stream(result.split(USER_ANSWER_DELIMITER)).map(Integer::valueOf).collect(Collectors.toList());
            return intResults.stream().allMatch(questionMap::containsKey) ? intResults : Collections.emptyList();
        } catch (NumberFormatException ex) {
            return Collections.emptyList();
        }
    }
}
